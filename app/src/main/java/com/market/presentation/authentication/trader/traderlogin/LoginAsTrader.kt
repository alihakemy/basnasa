package com.market.presentation.authentication.trader.traderlogin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.market.data.models.SendLogin
import com.market.data.models.get.login.LoginResponse
import com.market.databinding.ActivityLoginAsTraderBinding
import com.market.presentation.authentication.trader.create.traderjoin.JoinAsTraderActivity
import com.market.presentation.authentication.forget.ForgetPasswordStep1
import com.market.presentation.authentication.trader.create.tagercodeverify.TagerCodeVerificationActivity
import com.market.presentation.bases.BaseActivity
import com.market.presentation.location.MapsActivity
import com.market.utils.ResultState
import com.market.utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginAsTrader : BaseActivity() {

    private lateinit var binding: ActivityLoginAsTraderBinding
    private val viewModel: LoginAsTraderViewModel by viewModels()
    val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAsTraderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pd = ProgressDialog(this)
        pd.setMessage("loading")
        pd.setCancelable(false)
        binding.registerTager.setOnClickListener {

            val intent = Intent(this, JoinAsTraderActivity::class.java)
            startActivity(intent)
        }
        binding.textView3.setOnClickListener {

            val intent = Intent(this, ForgetPasswordStep1::class.java)
            startActivity(intent)
        }

        binding.imageView3.setOnClickListener {
            onBackPressed()
        }

        binding.button.setOnClickListener {


            if (!binding.passwordText.text.toString().isNullOrEmpty()) {
                if(!pd.isShowing){
                    pd?.show()
                }

                viewModel.loginTrader(
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
                    result.data?.let { it1 -> storeLoginData(it1) }
                    val intent = Intent(this, MapsActivity::class.java)
                    intent.putExtra(
                        "role",
                        result.data!!.data.user.Roles.toString().trim().toLowerCase()
                    )
                    startActivity(intent)
                    finish()

                }
                else -> {
                    if (pd.isShowing) {
                        pd.dismiss()
                    }
                    Log.e("MessageALI", result.message.toString())
                    if (result.message.equals("Sorry This account is not activated")) {
                        val intent = Intent(this, TagerCodeVerificationActivity::class.java)
                        bundle.putString("phone", binding.phoneTextTextPersonName.text.toString())
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    }

                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_LONG).show()

                }
            }
        })


    }
}