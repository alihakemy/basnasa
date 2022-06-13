package com.market.presentation.mainscreen.user.displayproduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.market.R
import com.market.databinding.ActivityDisplayProductBinding

class DisplayProduct : AppCompatActivity() {
    lateinit var binding:ActivityDisplayProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDisplayProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}