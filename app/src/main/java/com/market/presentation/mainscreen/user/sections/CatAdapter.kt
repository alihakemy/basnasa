package com.market.presentation.mainscreen.user.sections

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market.data.models.get.setions.SubCategory
import com.market.databinding.CatItemBinding
import com.market.databinding.SearchItemUserBinding

class CatAdapter(private  val subCategory: List<SubCategory>?,inline val
clicked:(subCategory:SubCategory)->Unit) :
    RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    inner class CatViewHolder(val binding:CatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(get: SubCategory?) {
            Glide.with(binding.icon.context).load(get?.imagePath.toString())
                .into(binding.icon)

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