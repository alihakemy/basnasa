package com.market.presentation.mainscreen.user.ui.offers.offeradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.like.LikeButton
import com.like.OnLikeListener
import com.market.BuildConfig
import com.market.data.models.get.offers.Merchant
import com.market.databinding.SearchItemUserBinding
import com.market.presentation.mainscreen.user.displaytrader.TraderProfileActivity


class UserOfferAdapter(val merchants: List<Merchant>?,inline val likeds:(boolean:Boolean,id:String)->Unit) :
    RecyclerView.Adapter<UserOfferAdapter.MerchantViewHolder1>() {


    inner class MerchantViewHolder1(var binding:SearchItemUserBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(get:Merchant) {

            Glide.with(binding.profileImage.context).load(get?.imagePath).into(binding.profileImage)
            binding.textView24.text=get?.name.toString()
            binding.textView26.text=get?.distance.toString()

            binding.textView25.text=get?.shopName.toString()
            binding.ratingBar.rating=get?.rate?.toFloat() ?:0.0f

            binding.ratingBar.numStars =4
            binding.starButton.isLiked= get?.favaurite == true

            if(BuildConfig.DEBUG){

                binding.ratingBar.rating=2f

            }

            binding.textView20.text ="("+ get?.rateCount+")"

            binding.starButton.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton?) {
                    get.favaurite= likeButton?.isLiked == true
                    likeButton?.isLiked?.let { likeds(it,get?.id.toString()) }
                }

                override fun unLiked(likeButton: LikeButton?) {
                    get.favaurite= likeButton?.isLiked == true

                    likeButton?.isLiked?.let { likeds(it,get?.id.toString()) }

                }

            })
            binding.root.setOnClickListener {
                TraderProfileActivity.startTagerProfile(get?.id.toString(), it.context)
            }
        }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserOfferAdapter.MerchantViewHolder1 {


        val view =SearchItemUserBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return MerchantViewHolder1(
            view
        )


    }

    override fun onBindViewHolder(holder: UserOfferAdapter.MerchantViewHolder1, position: Int) {

        merchants?.get(position)?.let { holder.bind(it) }

    }


    override fun getItemCount(): Int = merchants?.size ?:0
}