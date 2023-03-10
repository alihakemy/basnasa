package com.market.presentation.mainscreen.user.ui.home.merchants

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market.data.models.get.homeusers.Merchant
import com.market.databinding.UserMerchantsItemsBinding
import com.market.presentation.mainscreen.user.displaytrader.TraderProfileActivity.Companion.startTagerProfile


class UserMerchantsAdapter(private val merchants: List<Merchant>?) :
    RecyclerView.Adapter<UserMerchantsAdapter.UserMerchantsViewHolder>() {


    inner class UserMerchantsViewHolder(val binding: UserMerchantsItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(get: Merchant?) {

            Glide.with(binding.profileImage.context).load(get?.imagePath).into(binding.profileImage)
            binding.textView24.text = get?.name.toString()
            binding.textView26.text = get?.distance.toString()

            binding.textView25.text = get?.shopName.toString()
            binding.ratingBar.rating = get?.rate?.toFloat() ?: 0.0f

            binding.ratingBar.numStars = 4



            binding.textView20.text = "(" + get?.rateCount + ")"

            binding.root.setOnClickListener {
                startTagerProfile(get?.id.toString(), it.context)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMerchantsViewHolder {

        return UserMerchantsViewHolder(
            UserMerchantsItemsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: UserMerchantsViewHolder, position: Int) {

        holder.bind(merchants?.get(position))

    }

    override fun getItemCount(): Int = merchants?.size ?: 0
}