package com.dicoding.kisahrasa.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.kisahrasa.core.ui.model.RecipeUI
import com.dicoding.kisahrasa.core.utils.Resource
import com.dicoding.kisahrasa.databinding.FragmentHomeBinding
import com.dicoding.kisahrasa.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeAdapter = RecipeAdapter()
        val carouselAdapter = CarouselAdapter()

        recipeAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_RECIPE, selectedData)
            startActivity(intent)
        }

        carouselAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_RECIPE, selectedData)
            startActivity(intent)
        }

        setupUI(recipeAdapter, carouselAdapter)
        observeState(recipeAdapter, carouselAdapter)
    }

    private fun setupUI(recipeAdapter: RecipeAdapter, carouselAdapter: CarouselAdapter) {
        with(binding.rvRecipe) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = recipeAdapter
        }

        with(binding.viewPagerCarousel) {
            adapter = carouselAdapter
            offscreenPageLimit = 3
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                homeViewModel.setSearchQuery(newText ?: "")
                return true
            }
        })
    }

    private fun observeState(recipeAdapter: RecipeAdapter, carouselAdapter: CarouselAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.recipes.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            showLoading(true)
                        }
                        is Resource.Success -> {
                            val allRecipes = resource.data ?: emptyList()
                            if (allRecipes.isEmpty()) {
                                showEmpty(true, "No recipes found")
                            } else {
                                showSuccess(allRecipes, recipeAdapter, carouselAdapter)
                            }
                        }
                        is Resource.Error -> {
                            showError(resource.message ?: "Unknown Error")
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.scrollView.visibility = View.GONE
        binding.tvStateMessage.visibility = View.GONE
    }

    private fun showEmpty(isEmpty: Boolean, message: String) {
        binding.progressBar.visibility = View.GONE
        binding.scrollView.visibility = View.GONE
        binding.tvStateMessage.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.tvStateMessage.text = message
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.scrollView.visibility = View.GONE
        binding.tvStateMessage.visibility = View.VISIBLE
        binding.tvStateMessage.text = message
    }

    private fun showSuccess(allRecipes: List<RecipeUI>, recipeAdapter: RecipeAdapter, carouselAdapter: CarouselAdapter) {
        binding.progressBar.visibility = View.GONE
        binding.scrollView.visibility = View.VISIBLE
        binding.tvStateMessage.visibility = View.GONE
        
        carouselAdapter.submitList(allRecipes.take(5))
        recipeAdapter.submitList(if (allRecipes.size > 5) allRecipes.drop(5) else emptyList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
