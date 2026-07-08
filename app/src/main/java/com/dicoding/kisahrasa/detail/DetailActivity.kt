package com.dicoding.kisahrasa.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.kisahrasa.R
import com.dicoding.kisahrasa.core.ui.model.RecipeUI
import com.dicoding.kisahrasa.core.utils.loadImage
import com.dicoding.kisahrasa.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recipe = intent.getParcelableExtra<RecipeUI>(EXTRA_RECIPE)
        if (recipe != null) {
            showDetailRecipe(recipe)
        }

        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun showDetailRecipe(recipe: RecipeUI) {
        binding.tvDetailName.text = recipe.name
        binding.tvDetailCategory.text = recipe.category
        binding.tvDetailInstructions.text = recipe.instructions
        
        binding.ivDetailImage.loadImage(recipe.thumbUrl)

        var statusFavorite = recipe.isFavorite
        setStatusFavorite(statusFavorite)
        
        binding.fabFavorite.setOnClickListener {
            statusFavorite = !statusFavorite
            detailViewModel.setFavoriteRecipe(recipe, statusFavorite)
            setStatusFavorite(statusFavorite)
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
        } else {
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border))
        }
    }

    companion object {
        const val EXTRA_RECIPE = "extra_recipe"
    }
}
