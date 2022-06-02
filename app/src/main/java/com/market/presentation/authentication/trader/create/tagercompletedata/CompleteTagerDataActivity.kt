package com.market.presentation.authentication.trader.create.tagercompletedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.market.databinding.ActivityCompleteTagerDataBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EarlyEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class CompleteTagerDataActivity : AppCompatActivity() {

    private lateinit var binding :ActivityCompleteTagerDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityCompleteTagerDataBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }
}