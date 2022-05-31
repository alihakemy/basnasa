package com.market.presentation.authentication.user.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.market.R
import com.market.databinding.CreateUserAccountActivityBinding
import com.market.presentation.bases.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateUserAccountActivity : BaseActivity() {
    private lateinit var binding:CreateUserAccountActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=CreateUserAccountActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)






    }


}