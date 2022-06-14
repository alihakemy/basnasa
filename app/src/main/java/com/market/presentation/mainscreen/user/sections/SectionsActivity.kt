package com.market.presentation.mainscreen.user.sections

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.market.R
import com.market.databinding.SectionsActivityBinding

class SectionsActivity : AppCompatActivity() {
    lateinit var binding:SectionsActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=SectionsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}