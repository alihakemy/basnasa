package com.market.presentation.mainscreen.user.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.like.LikeButton
import com.like.OnLikeListener
import com.market.BuildConfig
import com.market.data.models.get.search.SearchResults
import com.market.databinding.SearchItemUserBinding
import com.market.presentation.mainscreen.user.displaytrader.TraderProfileActivity
import java.util.*


class SearchAdapter(private  val searchResults:SearchResults,val logined:Boolean,inline val likeds:(boolean:Boolean,id:String)->Unit) :
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
            binding.starButton.isLiked= get?.favaurite == true


            binding.textView20.text ="("+ get?.rate_count +")"

            binding.starButton.setOnLikeListener(object :OnLikeListener{
                override fun liked(likeButton: LikeButton?) {
                    if(logined){
                        get.favaurite= likeButton?.isLiked == true

                    }else
                    {
                        get.favaurite= likeButton?.isLiked == false
                    }
                    likeButton?.isLiked?.let { likeds(it,get?.id.toString()) }
                }

                override fun unLiked(likeButton: LikeButton?) {
                    get.favaurite= false

                    likeButton?.isLiked?.let { likeds(it,get?.id.toString()) }

                }

            })

            binding.root.setOnClickListener {
                TraderProfileActivity.startTagerProfile(get?.id.toString(), it.context)
            }


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

        searchResults?.data?.merchants?.get(position)?.let { holder.bind(it) }

    }

    override fun getItemCount(): Int = searchResults?.data?.merchants?.size ?:0
}