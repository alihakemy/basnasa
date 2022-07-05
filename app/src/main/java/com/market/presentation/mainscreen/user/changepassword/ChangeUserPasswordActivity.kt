package com.market.presentation.mainscreen.user.changepassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.market.databinding.ChangeUserPasswordActivityBinding

class ChangeUserPasswordActivity : AppCompatActivity() {
    lateinit var  binding:ChangeUserPasswordActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ChangeUserPasswordActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}