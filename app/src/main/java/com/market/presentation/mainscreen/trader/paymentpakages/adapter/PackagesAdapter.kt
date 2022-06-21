package com.market.presentation.mainscreen.trader.paymentpakages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.market.R
import com.market.data.models.get.paymentPackages.Package
import com.market.databinding.PackageItemBinding
import java.text.SimpleDateFormat
import java.util.*

open class PackagesAdapter(
    val mpackageSelected: List<Package>?,
    val clicked: (packageSelected: Package?) -> Unit
) :
    RecyclerView.Adapter<PackagesAdapter.PackagesViewHolder>() {
    inner class PackagesViewHolder(val binding: PackageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(packageSelected: Package?) {
            binding.textView100.text = packageSelected?.text1.toString()

            binding.textView75.text = packageSelected?.text2.toString()
            binding.textView76.text = packageSelected?.text3.toString()

            val userDob: Date = SimpleDateFormat("yyyy-MM-dd").parse(packageSelected?.startDate)
            val end: Date = SimpleDateFormat("yyyy-MM-dd").parse(packageSelected?.endDate)

            val diff: Long = userDob.time - end.time
            val numOfDays = (diff / (1000 * 60 * 60 * 24)).toInt()

            binding.textView79.text = "ساريه لمده$numOfDays يوم "
            if (packageSelected?.selected == true) {
                binding.imageView61.setImageResource(R.drawable.selected_icons)

            } else {
                binding.imageView61.setImageResource(R.drawable.ic_ellipse_493)
            }

            binding.root.setOnClickListener {

                mpackageSelected?.forEach {
                    it.selected=false
                }
                packageSelected?.selected=true
                binding.imageView61.setImageResource(R.drawable.selected_icons)
                notifyDataSetChanged()
                clicked(packageSelected)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackagesViewHolder {
        return PackagesViewHolder(
            PackageItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PackagesViewHolder, position: Int) {
        holder.bind(mpackageSelected?.get(position))
    }

    override fun getItemCount(): Int {
        return mpackageSelected?.size ?: 0
    }


}