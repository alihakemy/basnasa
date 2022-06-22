package com.market.presentation.mainscreen.trader.tagerpage.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market.data.models.get.tagerdetails.Product
import com.market.databinding.TagerItemBinding
import com.market.presentation.mainscreen.user.displayproduct.DisplayProduct.Companion.startDisplayProduct
import com.market.presentation.mainscreen.user.displaytrader.tageradapter.ProductAdapter

 class ProductAdapterTager(val product: List<com.market.data.models.get.tagerprofile.ProductItem>?) :
    RecyclerView.Adapter<ProductAdapterTager.ProductViewHolder>() {
    inner class ProductViewHolder(val binding: TagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product:com.market.data.models.get.tagerprofile.ProductItem?) {

            Glide.with(binding.root.context).load(product?.imagePath.toString()).into(binding.image)

            binding.textView62.text = product?.name.toString()
            binding.textView64.text = product?.prefitPrice.toString()

            binding.textView33.text = product?.mainprice.toString()
            binding.textView33.paintFlags =
                binding.textView33.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            binding.ratingBar.rating = product?.rate?.toString()?.toFloat()?: 0f

            binding.root.setOnClickListener {
               // startDisplayProduct(product?.id.toString(), it.context)
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            TagerItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(product?.get(position))
    }

    override fun getItemCount(): Int {
        return product?.size ?: 0
    }


}
