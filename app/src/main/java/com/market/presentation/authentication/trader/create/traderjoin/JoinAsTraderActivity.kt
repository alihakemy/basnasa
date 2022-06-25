package com.market.presentation.authentication.trader.create.traderjoin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.market.R
import com.market.data.models.SendRegister
import com.market.data.models.get.register.RegisterResponse
import com.market.databinding.JoinAsTraderActivityBinding
import com.market.presentation.authentication.trader.create.tagercodeverify.TagerCodeVerificationActivity
import com.market.presentation.authentication.user.create.CreateAccountViewModel
import com.market.presentation.mainscreen.termsandConditions.TermsAndConditions
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinAsTraderActivity : AppCompatActivity() {
    var binding:JoinAsTraderActivityBinding ?=null
    val viewModel: JoinAsTraderViewModel by viewModels()
    val bundle = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=JoinAsTraderActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val pd = ProgressDialog(this)
        pd.setMessage("loading")

        pd.setCancelable(false)

        binding?.textView6?.setOnClickListener {
            TermsAndConditions.startTerms("1",this)

        }

        binding?.imageView3?.setOnClickListener {
           onBackPressed()
        }
        binding?.button?.setOnClickListener {
            bundle.putString("userName", binding?.editTextTextPersonName2?.text.toString())
            bundle.putString("phone", binding?.editTextTextPersonName?.text.toString())
            bundle.putString("email", binding?.editTextTextPersonName3?.text.toString())
            bundle.putString("password", binding?.editTextTextPersonName4?.text.toString())
            bundle.putString("role", "Tager")

            pd.show()
            viewModel.registerTager(
                SendRegister(
                    name =
                    binding?.editTextTextPersonName2?.text.toString(),
                    password = binding?.editTextTextPersonName4?.text.toString(),
                    phone = binding?.editTextTextPersonName?.text.toString(),
                    email = binding?.editTextTextPersonName3?.text.toString(),
                    shop_name = binding?.shopeName?.text.toString(),
                    role = "Tager", Installed = "0"
                )
            ).observe(this, Observer {

                when (val result = it) {
                    is ResultState.Success<RegisterResponse> -> {
                        if (pd.isShowing) {
                            pd.dismiss()
                        }


                        val intent = Intent(this, TagerCodeVerificationActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                        Toast.makeText(
                           this,
                            result.data!!.data.activation_code.toString(),
                            Toast.LENGTH_LONG
                        ).show()


                    }
                    else -> {
                        Toast.makeText(this, result.message.toString(), Toast.LENGTH_LONG).show()

                        if (pd.isShowing) {
                            pd.dismiss()
                        }

                    }
                }


            })


        }

    }
}