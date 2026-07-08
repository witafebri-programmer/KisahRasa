package com.dicoding.kisahrasa.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.kisahrasa.databinding.ItemCarouselRecipeBinding
import com.dicoding.kisahrasa.core.ui.model.RecipeUI
import com.dicoding.kisahrasa.core.utils.loadImage

class CarouselAdapter : ListAdapter<RecipeUI, CarouselAdapter.CarouselViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((RecipeUI) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding = ItemCarouselRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(recipe)
        }
    }

    class CarouselViewHolder(private val binding: ItemCarouselRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeUI) {
            binding.tvCarouselTitle.text = recipe.name
            binding.ivCarouselImage.loadImage(recipe.thumbUrl)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecipeUI>() {
            override fun areItemsTheSame(oldItem: RecipeUI, newItem: RecipeUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RecipeUI, newItem: RecipeUI): Boolean {
                return oldItem == newItem
            }
        }
    }
}
