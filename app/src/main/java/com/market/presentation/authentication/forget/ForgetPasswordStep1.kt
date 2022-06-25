package com.market.presentation.authentication.forget

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.market.utils.ResultState
import com.market.data.models.ForgetPassword
import com.market.data.models.get.forgetpassword.GetForgetPassword
import com.market.databinding.ActivityForgetPasswordStep1Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordStep1 : AppCompatActivity() {

    lateinit var binding: ActivityForgetPasswordStep1Binding
    val viewModel: ResetPasswordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordStep1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val pd = ProgressDialog(this)
        pd.setMessage("loading")

        pd.setCancelable(false)
        binding.button.setOnClickListener {
            if (!binding.phoneNumber.text.isNullOrEmpty()) {
                pd.show()
                viewModel.forgetPassword(ForgetPassword(binding.phoneNumber.text.toString()))
                    .observe(this, Observer {
                        when (val result = it) {
                            is ResultState.Success<GetForgetPassword> -> {
                                if (pd.isShowing) {
                                    pd.dismiss()
                                }
                                Toast.makeText(
                                    this,
                                    result.data!!.data.toString(),
                                    Toast.LENGTH_LONG
                                ).show()

                                val intent = Intent(this, ForgetPasswordStep2::class.java)
                                intent.putExtra("phone", binding.phoneNumber.text.toString())
                                intent.putExtra("code",  result.data!!.data.toString())
                                startActivity(intent)

                                finish()
                            }
                            else -> {
                                if (pd.isShowing) {
                                    pd.dismiss()
                                }

                                Toast.makeText(
                                    this,
                                    "The الهاتف format is invalid",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }


                    })

            }

        }

    }
}