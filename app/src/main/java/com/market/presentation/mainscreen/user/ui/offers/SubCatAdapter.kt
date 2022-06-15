package com.market.presentation.mainscreen.user.ui.offers

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market.data.models.get.setions.SubCategory
import com.market.databinding.CatItemBinding

class SubCatAdapter(private val subCategory: List<com.market.data.models.get.offers.SubCategory>?, inline val
clicked:(subCategory: com.market.data.models.get.offers.SubCategory?)->Unit) :
    RecyclerView.Adapter<SubCatAdapter.CatViewHolder>() {

    inner class CatViewHolder(val binding:CatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(get: com.market.data.models.get.offers.SubCategory?) {

            binding.icon.visibility=View.GONE

            binding.text.text=get?.name.toString()
            binding.background.setBackgroundColor(Color.parseColor(get?.color))

            binding.root.setOnClickListener {
                get?.let { it1 -> clicked(it1) }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(
            CatItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(subCategory?.get(position))
    }

    override fun getItemCount(): Int {
        return subCategory?.size ?:0
    }


}