package com.market.presentation.mainscreen.user.ui.offers.offeradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market.data.models.get.fav.Data
import com.market.data.models.get.fav.Favourites
import com.market.databinding.FavUserItem1Binding
import com.market.databinding.FavUserItem2Binding


class UserOfferAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class UserCategoriesViewHolder1(var binding: FavUserItem1Binding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        val view = FavUserItem1Binding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return UserCategoriesViewHolder1(
            view
        )


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


    }


    override fun getItemCount(): Int = 50
}