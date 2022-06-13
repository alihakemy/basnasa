package com.market.presentation.mainscreen.user.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market.BuildConfig
import com.market.data.models.get.search.SearchResults
import com.market.databinding.SearchItemUserBinding


class SearchAdapter(private  val searchResults:SearchResults) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {


    inner class SearchViewHolder(val binding: SearchItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(get: com.market.data.models.get.search.Merchant) {

            Glide.with(binding.profileImage.context).load(get?.imagePath).into(binding.profileImage)
            binding.textView24.text=get?.name.toString()
            binding.textView26.text=get?.distance.toString()

            binding.textView25.text=get?.shop_name.toString()
            binding.ratingBar.rating=get?.rate?.toFloat() ?:0.0f

            binding.ratingBar.numStars =4
            binding.starButton.isLiked=get?.favaurite

            if(BuildConfig.DEBUG){

                    binding.ratingBar.rating=2f

            }

            binding.textView20.text ="("+ get?.rate_count +")"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        return SearchViewHolder(
            SearchItemUserBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        holder.bind(searchResults?.data.merchants.get(position))

    }

    override fun getItemCount(): Int = searchResults?.data.merchants.size ?:0
}