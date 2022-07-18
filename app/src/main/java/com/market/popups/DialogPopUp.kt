package com.market.popups

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.market.R
import com.market.data.models.get.popups.popups
import com.market.databinding.DialogpopBinding
import com.market.presentation.mainscreen.user.displayproduct.DisplayProduct
import com.market.presentation.mainscreen.user.displaytrader.TraderProfileActivity


class DialogPopUp(context: Context, val result: popups?) : Dialog(context) {

    init {
        setCancelable(true)

    }

    lateinit var binding: DialogpopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawableResource(R.drawable.rectangle_curved);
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogpopBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.imageView79.setOnClickListener {
            dismiss()
        }

        if (!result?.data?.slider.isNullOrEmpty()) {
            Glide.with(context).load(result?.data?.slider?.first()?.imagePath.toString())
                .into(binding.img)

            binding.img.setOnClickListener {

                if (result?.data?.slider?.first()?.typeDirection.toString().equals("product")) {
                    DisplayProduct.startDisplayProduct(
                        result?.data?.slider?.first()?.showNumber.toString(),
                        context
                    )

                } else if (result?.data?.slider?.first()?.typeDirection.toString()
                        .equals("tager")
                ) {
                    TraderProfileActivity.startTagerProfile(
                        result?.data?.slider?.first()?.showNumber.toString(),
                        context
                    )
                } else {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(result?.data?.slider?.first()?.showNumber.toString())
                        )
                    )

                }

            }


        }


    }
}