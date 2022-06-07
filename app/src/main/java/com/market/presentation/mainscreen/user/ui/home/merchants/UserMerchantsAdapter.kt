package com.market.presentation.mainscreen.user.ui.home.merchants

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.market.databinding.UserMerchantsItemsBinding


class UserMerchantsAdapter() :
    RecyclerView.Adapter<UserMerchantsAdapter.UserMerchantsViewHolder>() {


    inner class UserMerchantsViewHolder(binding: UserMerchantsItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

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

    }

    override fun getItemCount(): Int = 10
}