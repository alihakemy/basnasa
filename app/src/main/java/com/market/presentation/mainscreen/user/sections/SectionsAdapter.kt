package com.market.presentation.mainscreen.user.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market.BuildConfig
import com.market.data.models.get.setions.Merchant
import com.market.databinding.SearchItemUserBinding
import com.market.presentation.mainscreen.user.search.adapter.SearchAdapter

class SectionsAdapter(val merchants: List<Merchant>?) :
    RecyclerView.Adapter<SectionsAdapter. SectionsViewHolder>() {


    inner class SectionsViewHolder(val binding: SearchItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(merchants: Merchant?) {
            Glide.with(binding.profileImage.context).load(merchants?.imagePath.toString())
                .into(binding.profileImage)

            binding.textView24.text=merchants?.name.toString()
            binding.textView25.text=merchants?.shopName.toString()
            binding.textView20.text="("+merchants?.rateCount.toString()+")"
            binding.ratingBar.rating=merchants?.rate?.toFloat() ?:0.0f

            binding.ratingBar.numStars =4
            binding.starButton.isLiked= merchants?.favaurite == true

            if(BuildConfig.DEBUG){

                binding.ratingBar.rating=2f

            }
            binding.textView26.text=merchants?.distance

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionsViewHolder{

        return SectionsViewHolder(
            SearchItemUserBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: SectionsViewHolder, position: Int) {

        holder.bind(merchants?.get(position))

    }

    override fun getItemCount(): Int = merchants?.size ?:0
}