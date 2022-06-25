package com.market.presentation.authentication.forget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.market.R
import com.market.data.models.ResendCode
import com.market.databinding.ForgetPasswordActivityBinding
import com.market.presentation.authentication.trader.create.tagercodeverify.TagerCodeVerificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordStep2 : AppCompatActivity() {
    lateinit var binding: ForgetPasswordActivityBinding
    var timers: CountDownTimer? = null
    val viewModel: TagerCodeVerificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ForgetPasswordActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {

            if (!binding.otpView.value.toString().isNullOrEmpty()) {
                if (binding.otpView.value.toString().equals(getIntent().getStringExtra("code"))) {
                    val intent = Intent(this, ForgetPasswordStep3::class.java)
                    intent.putExtra("phone", getIntent().getStringExtra("phone"))
                    intent.putExtra("code", binding.otpView.value.toString())

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

        startTimer()
        binding?.textView13?.isVisible = false

        binding?.textView13?.setOnClickListener {
            binding?.textView13?.isVisible = false

            viewModel.resendCode(ResendCode(getIntent().getStringExtra("phone").toString(), "phone"))
            startTimer()
        }

    }
    fun startTimer() {
        timers = object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                kotlin.runCatching {

                    binding?.textView12?.text =
                        "أعادة إرسال الرمز خلال" + (millisUntilFinished / 1000).toString()
                    //here you can have your logic to set text to edittext
                }
            }

            override fun onFinish() {
                kotlin.runCatching {
                    binding?.textView12?.text = "0"
                    binding?.textView13?.isVisible = true
                }

            }
        }.start()
    }

    override fun onPause() {
        super.onPause()
        timers?.cancel()
    }
}