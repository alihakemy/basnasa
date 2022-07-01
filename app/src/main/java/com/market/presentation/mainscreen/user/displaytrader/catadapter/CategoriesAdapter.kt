package com.market.presentation.mainscreen.user.displaytrader.catadapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.market.data.models.get.tagerdetails.Category
import com.market.databinding.CatTagetItemBinding
import com.market.databinding.CommentsItemBinding
import com.market.databinding.SearchItemUserBinding

open class CategoriesAdapter(val categories: List<Category>? , val clicked:(cat: Category?)->Unit) :
    RecyclerView.Adapter<CategoriesAdapter.CatViewHolder>() {
    inner class CatViewHolder(val binding: CatTagetItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: Category?) {
            binding.line.isVisible = cat?.selected == true

            binding.textView65.text = cat?.name.toString()
            binding.root.setOnClickListener {
                categories?.forEach {
                    it.selected=false
                }
                clicked(cat)
                cat?.selected=true

                notifyDataSetChanged()



            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(
            CatTagetItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(categories?.get(position))
    }

    override fun getItemCount(): Int {
        return categories?.size ?: 0
    }


}