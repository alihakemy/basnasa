package com.market.presentation.mainscreen.user.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.market.databinding.FavUserItem1Binding
import com.market.databinding.FavUserItem2Binding

private const val LIST_ITEM = 0
private const val GRID_ITEM = 1

class UserFavAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isSwitchView = true

    inner class UserCategoriesViewHolder(binding: View) :
        RecyclerView.ViewHolder(binding.rootView)

    override fun getItemViewType(position: Int): Int {
        return if (isSwitchView) {
            LIST_ITEM
        } else {
            GRID_ITEM
        }
    }

    fun toggleItemViewType(): Boolean {
        isSwitchView = !isSwitchView
        return isSwitchView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == LIST_ITEM) {
            val view = FavUserItem1Binding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
            return UserCategoriesViewHolder(
                view.root
            )

        } else {
            return UserCategoriesViewHolder(
                FavUserItem2Binding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                ).root
            )
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 100
}