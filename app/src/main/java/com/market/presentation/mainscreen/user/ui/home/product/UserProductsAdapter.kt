package com.market.presentation.mainscreen.user.ui.home.product

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market.data.models.get.homeusers.Product
import com.market.databinding.UserProductitemHomeBinding
import com.market.presentation.mainscreen.user.displayproduct.DisplayProduct


class UserProductsAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<UserProductsAdapter.UserMerchantsViewHolder>() {


    inner class UserMerchantsViewHolder(val binding: UserProductitemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {

            Glide.with(binding.imageView14.context).load(product.imagePath)
                .into(binding.imageView14)

            binding.textView29.text = product.name
            binding.textView31.text = product.tager
            binding.textView34.text = product.content

            binding.textView32.text = product.mainprice

            binding.textView33.text = product.prefitPrice
            binding.textView33.paintFlags =
                binding.textView33.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            Glide.with(binding.circleImageView.context).load(product.tagerImage)
                .into(binding.circleImageView)

            binding.root.setOnClickListener {
                DisplayProduct.startDisplayProduct(product.id.toString(), it.context)
            }


        }
    }

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

        holder.bind(products[position])

    }

    override fun getItemCount(): Int = products.size
}