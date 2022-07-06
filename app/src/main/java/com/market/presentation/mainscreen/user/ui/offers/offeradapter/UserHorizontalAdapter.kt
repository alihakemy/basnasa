package com.market.presentation.mainscreen.user.ui.offers.offeradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market.BuildConfig
import com.market.data.models.get.offers.Merchant
import com.market.data.models.get.offers.SubCategory
import com.market.databinding.OfferHorizontalBinding
import com.market.databinding.SearchItemUserBinding
import com.market.presentation.mainscreen.user.displaytrader.TraderProfileActivity


class UserHorizontalAdapter(
    val merchants: List<Merchant>?) :
    RecyclerView.Adapter<UserHorizontalAdapter.MerchantViewHolder1>() {


    inner class MerchantViewHolder1(var binding:OfferHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(get: Merchant) {
            Glide.with(binding.image.context)
                .load(get.imagePath?.toString())
                .into(binding.image)
            binding.textView30.text = get.shopName?.toString()
            binding.textView26.text=get?.distance.toString()
            binding.textView35.text = get.name.toString()
            binding.textView.text = "(" + get.rateCount + ")"
            binding.ratingBar.rating = get.rate?.toFloat() ?: 0f
           binding.root.setOnClickListener {
                TraderProfileActivity.startTagerProfile(get?.id.toString(), it.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHorizontalAdapter.MerchantViewHolder1 {


        val view =OfferHorizontalBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return MerchantViewHolder1(
            view
        )


    }

    override fun onBindViewHolder(holder: UserHorizontalAdapter.MerchantViewHolder1, position: Int) {

        merchants?.get(position)?.let { holder.bind(it) }

    }


    override fun getItemCount(): Int = merchants?.size ?:0
}