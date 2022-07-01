package com.market.presentation.mainscreen.user.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.like.LikeButton
import com.like.OnLikeListener
import com.market.BuildConfig
import com.market.data.models.get.setions.Merchant
import com.market.databinding.SearchItemUserBinding
import com.market.presentation.mainscreen.user.displaytrader.TraderProfileActivity
import com.market.presentation.mainscreen.user.search.adapter.SearchAdapter

class SectionsAdapter(val merchants: List<Merchant>?,val logined:Boolean,inline val likeds:(boolean:Boolean,id:String)->Unit) :
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


            binding.textView26.text=merchants?.distance

            binding.starButton.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton?) {
                    if(logined){
                        merchants?.favaurite= likeButton?.isLiked == true

                    }else{
                        merchants?.favaurite= likeButton?.isLiked == false
                    }
                    likeButton?.isLiked?.let { likeds(it,merchants?.id.toString()) }
                }

                override fun unLiked(likeButton: LikeButton?) {
                    merchants?.favaurite= false

                    likeButton?.isLiked?.let { likeds(it,merchants?.id.toString()) }

                }

            })


            binding.root.setOnClickListener {
                TraderProfileActivity.startTagerProfile(merchants?.id.toString(), it.context)
            }

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