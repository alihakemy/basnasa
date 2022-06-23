package com.market.presentation.mainscreen.trader.addproduct.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market.R
import de.hdodenhof.circleimageview.CircleImageView

class ImageAdapter(
    var list: ArrayList<String>?,
    var con: Context,
    inline val remove: (index: Int) -> Unit
) : RecyclerView.Adapter<ImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.circle_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        Glide.with(con).load(list?.get(position)).into(holder.img)
        holder.close.setOnClickListener {

            list?.let { it1 -> remove(position) }
        }


    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var img: CircleImageView
    lateinit var close: android.widget.ImageView

    init {
        img = itemView.findViewById(R.id.circle_img)
        close = itemView.findViewById(R.id.imageView5)
    }
}