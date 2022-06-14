package com.market.presentation.mainscreen.user.ui.home.categories

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market.data.models.get.homeusers.Category
import com.market.databinding.UserCategoriesItemsBinding
import com.market.presentation.mainscreen.user.sections.SectionsActivity


class UserCategoriesAdapter(private  val categories: List<Category>) :
    RecyclerView.Adapter<UserCategoriesAdapter.UserCategoriesViewHolder>() {


    inner class UserCategoriesViewHolder(val binding: UserCategoriesItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {

            Glide.with(binding.imageView11.context).load(
                category.imagePath
            ).into(binding.imageView11)
            Glide.with(binding. imageView18.context).load(
                category.image_path_hidden
            ).into(binding. imageView18)

            binding.parent.setBackgroundColor(Color.parseColor(category.color))

            binding.textView22.text=category.name
            binding.textView23.text=category.content

            binding.root.setOnClickListener {
                SectionsActivity.startSections(category.id.toString(),binding.root.context)
            }


        }
    }

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

        holder.bind(categories[position])

    }

    override fun getItemCount(): Int = categories.size
}