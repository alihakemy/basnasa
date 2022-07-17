package com.market.popups

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import com.market.R
import com.market.databinding.DialogpopBinding
import dagger.hilt.android.AndroidEntryPoint


class DialogPopUp(context: Context) : Dialog(context) {

    init {
        setCancelable(true)

    }

    lateinit var binding:DialogpopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.decorView?.layoutDirection = View.LAYOUT_DIRECTION_RTL;
        window?.setBackgroundDrawableResource(R.drawable.rectangle_curved);
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding=DialogpopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView79.setOnClickListener {
            dismiss()
        }



    }
}