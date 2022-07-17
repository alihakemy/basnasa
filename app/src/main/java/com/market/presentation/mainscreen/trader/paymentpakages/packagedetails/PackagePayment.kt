package com.market.presentation.mainscreen.trader.paymentpakages.packagedetails

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.google.gson.Gson
import com.market.BuildConfig
import com.market.R
import com.market.data.models.get.addComment.DefaultResponse
import com.market.data.models.get.paymentPackages.Package
import com.market.databinding.ActivityPackagePaymentBinding
import com.market.presentation.MainActivity
import com.market.presentation.bases.BaseActivity
import com.market.utils.ResultState
import com.myfatoorah.sdk.entity.executepayment.MFExecutePaymentRequest
import com.myfatoorah.sdk.entity.initiatepayment.MFInitiatePaymentRequest
import com.myfatoorah.sdk.entity.initiatepayment.MFInitiatePaymentResponse
import com.myfatoorah.sdk.entity.paymentstatus.MFGetPaymentStatusRequest
import com.myfatoorah.sdk.entity.paymentstatus.MFGetPaymentStatusResponse
import com.myfatoorah.sdk.entity.sendpayment.MFSendPaymentRequest
import com.myfatoorah.sdk.entity.sendpayment.MFSendPaymentResponse
import com.myfatoorah.sdk.utils.*
import com.myfatoorah.sdk.views.MFResult
import com.myfatoorah.sdk.views.MFSDK
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PackagePayment : BaseActivity() {
    val TAG = "PackagePayment"
    lateinit var binding: ActivityPackagePaymentBinding
    val viewModel: SelectPackageViewModel by viewModels()
    lateinit var intentData: Package
    var paymentMethod =1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackagePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        intentData = intent.getParcelableExtra<Package>("item")!!
//        if (BuildConfig.DEBUG) {
//            MFSDK.init(
//                "rLtt6JWvbUHDDhsZnfpAhpYk4dxYDQkbcPTyGaKp2TYqQgG7FGZ5Th_WD53Oq8Ebz6A53njUoo1w3pjU1D4vs_ZMqFiz_j0urb_BH9Oq9VZoKFoJEDAbRZepGcQanImyYrry7Kt6MnMdgfG5jn4HngWoRdKduNNyP4kzcp3mRv7x00ahkm9LAK7ZRieg7k1PDAnBIOG3EyVSJ5kK4WLMvYr7sCwHbHcu4A5WwelxYK0GMJy37bNAarSJDFQsJ2ZvJjvMDmfWwDVFEVe_5tOomfVNt6bOg9mexbGjMrnHBnKnZR1vQbBtQieDlQepzTZMuQrSuKn-t5XZM7V6fCW7oP-uXGX-sMOajeX65JOf6XVpk29DP6ro8WTAflCDANC193yof8-f5_EYY-3hXhJj7RBXmizDpneEQDSaSz5sFk0sV5qPcARJ9zGG73vuGFyenjPPmtDtXtpx35A-BVcOSBYVIWe9kndG3nclfefjKEuZ3m4jL9Gg1h2JBvmXSMYiZtp9MR5I6pvbvylU_PP5xJFSjVTIz7IQSjcVGO41npnwIxRXNRxFOdIUHn0tjQ-7LwvEcTXyPsHXcMD8WtgBh-wxR8aKX7WPSsT1O8d8reb2aR7K3rkV3K82K_0OgawImEpwSvp9MNKynEAJQS6ZHe_J_l77652xwPNxMRTMASk1ZsJL",
//                MFCountry.KUWAIT,
//                MFEnvironment.TEST
//            )
//
//        } else {
//            MFSDK.init("rdihYRkPQ1922NG2QYRtq4U69NUHxteS-Jn1p2E3B9odNxhmphbGltcL-5E97l1KqveeTnQjfJ1lTPT7DygVd6glt-gj0DhonHgZ5Qe7QFQyuBrqnYycuMR3perDo6cmwzmcEhgwem8vngjbWH8MhVn3Lehfln-d9V4yO7SEkIWaHKt9fZTNSg71kbcTV1xQv8OHTdwoWzjgCYy7FwZQLsgsdAYeQ0gM51Y-HMzwprOzVE1CAl4gHuFmK0_18n_UIBULQzzWp6fijBjFWjLh-WdQRjvgWnamfTv2hLcNQ_Zxc1VzFFdcyK7_YJxsP9t4KTJl1Ip3mEfDAnklx_uM5KBc8a256RZfGFoCrVIsKP-R4LpMSN1haIojw8ZApeabH0s5FK0kAEelmicdIIx3XB2HhcROrxVzQmwMkM4z88gOyFIauNxgeJcqvvDhCS1KAsM5U3N34TvyNuDHipdHhlpOHUgw72vgJLaa-DlmAPGv_mhYH8yzWK7dDd-MMTPGMCZ9bDuYdX8U0X0oPLgDjZAb1lGVsaCk1D8UB4OGP5-90ik78W_-6-N-OVyX8YcXxTbmVQlYhEr1Er4DqHEP3SyCphj1sT02C6N_M22NsxOeVBIUgNByUtvttntTG9VZ3Pt07g29C-B0egzCKg9LJMpmnNjOjtteSiwGl4WZTbemJj8NP-USVIzN7m1kI32Y-KWUltDTg8bjl59BN6x6_urtRlFYM1MR6xWpAqAHHafQEeERSjz6GSh2tfxxaEKLTPYzTw", MFCountry.KUWAIT, MFEnvironment.LIVE)
//
//
//        }
        MFSDK.init(
            "rdihYRkPQ1922NG2QYRtq4U69NUHxteS-Jn1p2E3B9odNxhmphbGltcL-5E97l1KqveeTnQjfJ1lTPT7DygVd6glt-gj0DhonHgZ5Qe7QFQyuBrqnYycuMR3perDo6cmwzmcEhgwem8vngjbWH8MhVn3Lehfln-d9V4yO7SEkIWaHKt9fZTNSg71kbcTV1xQv8OHTdwoWzjgCYy7FwZQLsgsdAYeQ0gM51Y-HMzwprOzVE1CAl4gHuFmK0_18n_UIBULQzzWp6fijBjFWjLh-WdQRjvgWnamfTv2hLcNQ_Zxc1VzFFdcyK7_YJxsP9t4KTJl1Ip3mEfDAnklx_uM5KBc8a256RZfGFoCrVIsKP-R4LpMSN1haIojw8ZApeabH0s5FK0kAEelmicdIIx3XB2HhcROrxVzQmwMkM4z88gOyFIauNxgeJcqvvDhCS1KAsM5U3N34TvyNuDHipdHhlpOHUgw72vgJLaa-DlmAPGv_mhYH8yzWK7dDd-MMTPGMCZ9bDuYdX8U0X0oPLgDjZAb1lGVsaCk1D8UB4OGP5-90ik78W_-6-N-OVyX8YcXxTbmVQlYhEr1Er4DqHEP3SyCphj1sT02C6N_M22NsxOeVBIUgNByUtvttntTG9VZ3Pt07g29C-B0egzCKg9LJMpmnNjOjtteSiwGl4WZTbemJj8NP-USVIzN7m1kI32Y-KWUltDTg8bjl59BN6x6_urtRlFYM1MR6xWpAqAHHafQEeERSjz6GSh2tfxxaEKLTPYzTw",
            MFCountry.KUWAIT,
            MFEnvironment.LIVE
        )

        // You can custom your action bar, but this is optional not required to set this line
        MFSDK.setUpActionBar(
            "BS NAS Payment", R.color.statusBarColor,
            R.color.statusBarColor, true
        )


        intentData?.price?.let { initiatePayment(it) }

        binding.textView100.text = intentData?.text1.toString()

        binding.textView75.text = intentData?.text2.toString()
        binding.textView76.text = intentData?.text3.toString()


        val numOfDays =intentData?.ActivateCount

        binding.textView79.text = "ساريه لمده$numOfDays يوم "

        binding.imageView59.setImageResource(R.drawable.ic_componendfdt_6___18)

        binding.imageView59.setOnClickListener {
            paymentMethod=1
            binding.imageView59.setImageResource(R.drawable.ic_componendfdt_6___18)
            binding.imageView62.setImageResource(R.drawable.ic_componendfdt_6___1)
            binding.imageView63.setImageResource(R.drawable.ic_componendfdt_6___1)


        }
        binding.imageView62.setOnClickListener {
            paymentMethod=2
            binding.imageView59.setImageResource(R.drawable.ic_componendfdt_6___1)
            binding.imageView62.setImageResource(R.drawable.ic_componendfdt_6___18)
            binding.imageView63.setImageResource(R.drawable.ic_componendfdt_6___1)

        }
        binding.imageView63.setOnClickListener {
            paymentMethod=2
            binding.imageView59.setImageResource(R.drawable.ic_componendfdt_6___1)
            binding.imageView62.setImageResource(R.drawable.ic_componendfdt_6___1)
            binding.imageView63.setImageResource(R.drawable.ic_componendfdt_6___18)

        }
        binding.textView87.text = intentData?.price.toString() + "K.D"

        binding.textView86.setOnClickListener {

            intentData?.price?.let { it1 -> executePayment(paymentMethod, it1) }

        }


    }

    private fun initiatePayment(price: Double) {

        val request = MFInitiatePaymentRequest(price, MFCurrencyISO.KUWAIT_KWD)

        MFSDK.initiatePayment(
            request,
            MFAPILanguage.EN
        ) { result: MFResult<MFInitiatePaymentResponse> ->
            when (result) {
                is MFResult.Success -> {
                    Log.d(TAG, "Response: " + Gson().toJson(result.response))
                }
                is MFResult.Fail -> {
                    Log.d(TAG, "Fail: " + Gson().toJson(result.error))
                }
            }
        }
    }

    private fun executePayment(paymentMethod: Int, price: Double) {
        val request = MFExecutePaymentRequest(paymentMethod, price)

        var invoiceId: String = ""
        MFSDK.executePayment(
            this,
            request,
            MFAPILanguage.AR,
            onInvoiceCreated = {
                invoiceId = it
                Log.d(TAG, "invoiceId: $it")
            }
        ) { invoiceId: String, result: MFResult<MFGetPaymentStatusResponse> ->
            when (result) {
                is MFResult.Success -> {
                    Log.d(TAG, "Response: " + Gson().toJson(result.response))
                    sendPayment(price)

                    viewModel.subscribePackage(intentData.id.toString(), invoiceId)

                }
                is MFResult.Fail -> {
                    Log.d(TAG, "Fail: " + Gson().toJson(result.error))

                    showAlertDialog(Gson().toJson(result.error))

                }
            }
        }
        viewModel.result.observe(this, androidx.lifecycle.Observer {

            when (val result = it) {
                is ResultState.Success<DefaultResponse> -> {

                    binding.done.isVisible = true

                    finish()
                }
                else -> {
                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_LONG).show()
                }
            }

        })

      //  viewModel.subscribePackage(intentData.id.toString(), invoiceId)


    }


    private fun showAlertDialog(text: String) {
        AlertDialog.Builder(this)
            .setMessage(text)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    // to send payment to mail or phone number
    private fun sendPayment(amount: Double) {

        val request = MFSendPaymentRequest(
            amount,
            getLoginData()?.data?.user?.name, MFNotificationOption.LINK
        )
        request.customerEmail =
            getLoginData()?.data?.user?.email // The email required if you choose MFNotificationOption.ALL or MFNotificationOption.EMAIL
        request.customerMobile =
            getLoginData()?.data?.user?.phone // The mobile required if you choose MFNotificationOption.ALL or MFNotificationOption.SMS
        request.mobileCountryCode = MFMobileISO.KUWAIT.code

        MFSDK.sendPayment(request, MFAPILanguage.AR) { result: MFResult<MFSendPaymentResponse> ->
            when (result) {
                is MFResult.Success -> {
                    Log.d(TAG, "Response: " + Gson().toJson(result.response))
                }
                is MFResult.Fail -> {
                    Log.d(TAG, "Fail: " + Gson().toJson(result.error))
                }
            }

        }
    }


}