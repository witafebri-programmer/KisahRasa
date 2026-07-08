package com.dicoding.kisahrasa.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.kisahrasa.databinding.ItemListRecipeBinding
import com.dicoding.kisahrasa.core.ui.model.RecipeUI
import com.dicoding.kisahrasa.core.utils.loadImage

class RecipeAdapter : ListAdapter<RecipeUI, RecipeAdapter.RecipeViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((RecipeUI) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemListRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(recipe)
        }
    }

    class RecipeViewHolder(private val binding: ItemListRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeUI) {
            binding.tvItemTitle.text = recipe.name
            binding.tvItemDescription.text = recipe.category
            binding.ivItemImage.loadImage(recipe.thumbUrl)
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
