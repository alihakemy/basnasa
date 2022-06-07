package com.market.presentation.mainscreen.user.ui.home.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.market.databinding.UserCategoriesItemsBinding


class UserCategoriesAdapter() :
    RecyclerView.Adapter<UserCategoriesAdapter.UserCategoriesViewHolder>() {


    inner class UserCategoriesViewHolder(binding: UserCategoriesItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCategoriesViewHolder {

        return UserCategoriesViewHolder(
            UserCategoriesItemsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: UserCategoriesViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 5
}