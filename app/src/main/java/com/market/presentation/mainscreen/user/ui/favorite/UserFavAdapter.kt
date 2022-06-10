package com.market.presentation.mainscreen.user.ui.favorite

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

private const val LIST_ITEM = 0
private const val GRID_ITEM = 1

class UserFavAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val list: ArrayList<Data> = ArrayList()
    var isSwitchView = true

    inner class UserCategoriesViewHolder1(var binding: FavUserItem1Binding) :
        RecyclerView.ViewHolder(binding.root)


    inner class UserCategoriesViewHolder(var binding: FavUserItem2Binding) :
        RecyclerView.ViewHolder(binding.root)


    fun setList(list: ArrayList<Data>) {
        this.list.clear()
        this.list.addAll(list)
    }


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
            return UserCategoriesViewHolder1(
                view
            )

        } else {
            return UserCategoriesViewHolder(
                FavUserItem2Binding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        if (holder is UserCategoriesViewHolder) {
            Glide.with(holder.binding.image.context).load(list[position].image_path?.toString()).into(holder.binding.image)
            holder.binding.textView30.text=list[position].name?.toString()
            holder.binding.textView35.text=list[position].meta_description?.toString()

        } else  if (holder is UserCategoriesViewHolder1 ) {
            Glide.with(holder.binding.image.context).load(list[position].image_path?.toString()).into(holder.binding.image)
            holder.binding.textView30.text=list[position].name?.toString()
            holder.binding.textView35.text=list[position].meta_description?.toString()
        }



    }




    override fun getItemCount(): Int = list.size
}