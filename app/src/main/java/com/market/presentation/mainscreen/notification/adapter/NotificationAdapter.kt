package com.market.presentation.mainscreen.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market.data.models.get.notification.DataX
import com.market.databinding.NotificationItemBinding
import java.text.SimpleDateFormat
import java.util.*


class NotificationAdapter(private val notificationItem: List<DataX>?) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {


    inner class NotificationViewHolder(val binding: NotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(get: DataX) {

            Glide.with(binding.circleImg.context).load(get.image.toString()).into(binding.circleImg)
            binding.textView95.text = get.message.toString()



            binding.textView97.text = " منذو " + get.date  + "ساعه" + get.time


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {

        return NotificationViewHolder(
            NotificationItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {

        notificationItem?.get(position)?.let { holder.bind(it) }

    }

    override fun getItemCount(): Int = notificationItem?.size ?: 0
}