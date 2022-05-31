package com.market.presentation.authentication.user.forget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.market.R
import com.market.databinding.ForgetPasswordActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordStep2 : AppCompatActivity() {
    lateinit var binding: ForgetPasswordActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ForgetPasswordActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {

            if (!binding.otpView.otp.toString().isNullOrEmpty()) {
                if (binding.otpView.otp.toString().equals(getIntent().getStringExtra("code"))) {
                    val intent = Intent(this, ForgetPasswordStep3::class.java)
                    intent.putExtra("phone", getIntent().getStringExtra("phone"))
                    intent.putExtra("code", binding.otpView.otp.toString())

                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "كود غير صحيح",
                        Toast.LENGTH_LONG
                    ).show()

                }


            }

        }

    }
}