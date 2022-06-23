package com.market.presentation.authentication.trader.create.tagercodeverify

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.market.data.models.ResendCode
import com.market.data.models.SendVerificationPhone
import com.market.data.models.get.verificationPhone.VerificationPhone
import com.market.databinding.TagerCodeVerificationActivityBinding
import com.market.presentation.authentication.trader.create.tagercompletedata.CompleteTagerDataActivity
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TagerCodeVerificationActivity : AppCompatActivity() {
    var binding: TagerCodeVerificationActivityBinding? = null
    val viewModel: TagerCodeVerificationViewModel by viewModels()
    var timers: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TagerCodeVerificationActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val pd = ProgressDialog(this)
        pd.setMessage("loading")
        pd.setCancelable(false)
        binding?.textView11?.setOnClickListener {

            onBackPressed()

        }


        val phone = intent.extras?.getString("phone")

        binding?.textView10?.text = phone.toString()

        binding?.button?.setOnClickListener {

            pd.show()
            viewModel.verificationPhone(
                SendVerificationPhone(
                    phone.toString(),
                    binding?.otpView?.value.toString()
                )
            ).observe(this, Observer {

                when (val result = it) {

                    is ResultState.Success<VerificationPhone> -> {
                        if (pd.isShowing) {
                            pd.dismiss()
                        }
                        val intent = Intent(this, CompleteTagerDataActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        viewModel.storeToken(result.data!!.token.toString())
                        intent.putExtra("token", result.data!!.token.toString())
                        startActivity(intent)
                        finish()

                    }
                    else -> {
                        if (pd.isShowing) {
                            pd.dismiss()
                        }
                    }

                }


            })


        }

        binding?.textView13?.isVisible = false

        startTimer()

        binding?.textView13?.setOnClickListener {
            binding?.textView13?.isVisible = false

            viewModel.resendCode(ResendCode(phone.toString(), "phone"))
            startTimer()
        }

    }

    fun startTimer() {
        timers = object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                binding?.textView12?.text =
                    "أعادة إرسال الرمز خلال" + (millisUntilFinished / 1000).toString()
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                binding?.textView12?.text = "0"
                binding?.textView13?.isVisible = true
            }
        }.start()
    }

    override fun onPause() {
        super.onPause()
        timers?.cancel()
    }
}