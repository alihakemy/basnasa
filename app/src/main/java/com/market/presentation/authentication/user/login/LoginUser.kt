package com.market.presentation.authentication.user.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.market.data.models.SendLogin
import com.market.data.models.get.login.LoginResponse
import com.market.databinding.LoginUserActivityBinding
import com.market.presentation.authentication.trader.traderlogin.LoginAsTrader
import com.market.presentation.authentication.user.create.CreateUserAccountActivity
import com.market.presentation.bases.BaseActivity
import com.market.utils.ResultState
import com.market.presentation.authentication.forget.ForgetPasswordStep1
import com.market.presentation.location.MapsActivity
import com.market.utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginUser : BaseActivity() {

    private lateinit var binding: LoginUserActivityBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginUserActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pd = ProgressDialog(this)
        pd.setMessage("loading")

        pd.setCancelable(false)
        binding.registerUser.setOnClickListener {

            val intent = Intent(this, CreateUserAccountActivity::class.java)
            startActivity(intent)

        }
        binding.textView3.setOnClickListener {

            val intent = Intent(this, ForgetPasswordStep1::class.java)
            startActivity(intent)
        }



        binding.textView4.setOnClickListener {
            val intent = Intent(this, LoginAsTrader::class.java)
            startActivity(intent)

        }





        binding.button.setOnClickListener {


                if (!binding.passwordText.text.toString().isNullOrEmpty()) {
                    pd.show()
                    viewModel.loginUser(
                        SendLogin(
                            binding.phoneTextTextPersonName.text.toString(),
                            binding.passwordText.text.toString()
                        )
                    )

                } else {
                    Toast.makeText(this, "تحقق من كلمه السر ", Toast.LENGTH_LONG).show()

                }



        }

        viewModel.loginResults.observe(this, Observer {

            when (val result = it) {
                is ResultState.Success<LoginResponse> -> {

                    result.data?.let { it1 -> storeLoginData(it1)
                        val intent = Intent(this, MapsActivity::class.java)
                        intent.putExtra("role",result.data?.data?.user?.Roles)
                        startActivity(intent)
                        finish()

                    }


                }
                else -> {
                    if (pd.isShowing) {
                        pd.dismiss()
                    }
                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_LONG).show()

                }


            }


        })

    }
}