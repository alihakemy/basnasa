package com.market.presentation.authentication.trader.traderlogin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.market.data.models.SendLogin
import com.market.data.models.get.login.LoginResponse
import com.market.databinding.ActivityLoginAsTraderBinding
import com.market.presentation.authentication.user.forget.ForgetPasswordStep1
import com.market.presentation.location.MapsActivity
import com.market.utils.ResultState
import com.market.utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginAsTrader : AppCompatActivity() {

    private lateinit var binding: ActivityLoginAsTraderBinding
    private val viewModel: LoginAsTraderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAsTraderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pd = ProgressDialog(this)
        pd.setMessage("loading")
        pd.setCancelable(false)
        binding.textView3.setOnClickListener {

            val intent = Intent(this, ForgetPasswordStep1::class.java)
            startActivity(intent)
        }

        binding.imageView3.setOnClickListener {
            onBackPressed()
        }

        binding.button.setOnClickListener {

            if (binding.phoneTextTextPersonName.text.toString().isValidEmail()) {
                if (!binding.passwordText.text.toString().isNullOrEmpty()) {
                    pd.show()
                    viewModel.loginTrader(
                        SendLogin(
                            binding.phoneTextTextPersonName.text.toString(),
                            binding.passwordText.text.toString()
                        )
                    )

                } else {
                    Toast.makeText(this, "تحقق من كلمه السر ", Toast.LENGTH_LONG).show()

                }

            } else {

                Toast.makeText(this, "تحقق من البريد الالكترونى ", Toast.LENGTH_LONG).show()

            }

        }

        viewModel.loginResults.observe(this, Observer {

            when (val result = it) {
                is ResultState.Success<LoginResponse> -> {
                    viewModel.storeLogin(result.data.data.user)
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                else -> {
                    if (pd.isShowing) {
                        pd.dismiss()
                    }
                    Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show()

                }
            }
        })


    }
}