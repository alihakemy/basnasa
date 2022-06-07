package com.market.presentation.mainscreen.user.ui.home.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.market.databinding.UserMerchantsItemsBinding
import com.market.databinding.UserProductitemHomeBinding


class UserProductsAdapter() :
    RecyclerView.Adapter<UserProductsAdapter.UserMerchantsViewHolder>() {


    inner class UserMerchantsViewHolder(binding: UserProductitemHomeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMerchantsViewHolder {

        return UserMerchantsViewHolder(
            UserProductitemHomeBinding.inflate(
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