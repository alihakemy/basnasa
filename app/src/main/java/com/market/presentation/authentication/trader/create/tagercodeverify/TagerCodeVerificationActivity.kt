package com.market.presentation.authentication.trader.create.tagercodeverify

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.market.R
import com.market.data.models.SendVerificationPhone
import com.market.data.models.get.verificationPhone.VerificationPhone
import com.market.databinding.TagerCodeVerificationActivityBinding
import com.market.presentation.authentication.trader.create.tagercompletedata.CompleteTagerDataActivity
import com.market.presentation.location.MapsActivity
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class TagerCodeVerificationActivity : AppCompatActivity() {
    var binding: TagerCodeVerificationActivityBinding? = null
    val viewModel: TagerCodeVerificationViewModel by viewModels()
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
                        intent.putExtra("token",result.data!!.token.toString())
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

    }
}