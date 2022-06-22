package com.market.presentation.mainscreen.trader.paymentpakages.packagedetails

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.google.gson.Gson
import com.market.BuildConfig
import com.market.R
import com.market.data.models.get.paymentPackages.Package
import com.market.databinding.ActivityPackagePaymentBinding
import com.market.presentation.bases.BaseActivity
import com.myfatoorah.sdk.entity.executepayment.MFExecutePaymentRequest
import com.myfatoorah.sdk.entity.initiatepayment.MFInitiatePaymentRequest
import com.myfatoorah.sdk.entity.initiatepayment.MFInitiatePaymentResponse
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackagePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val gson = Gson()

        val intentData = intent.getParcelableExtra<Package>("item")
        if (BuildConfig.DEBUG) {
            MFSDK.init(
                "rLtt6JWvbUHDDhsZnfpAhpYk4dxYDQkbcPTyGaKp2TYqQgG7FGZ5Th_WD53Oq8Ebz6A53njUoo1w3pjU1D4vs_ZMqFiz_j0urb_BH9Oq9VZoKFoJEDAbRZepGcQanImyYrry7Kt6MnMdgfG5jn4HngWoRdKduNNyP4kzcp3mRv7x00ahkm9LAK7ZRieg7k1PDAnBIOG3EyVSJ5kK4WLMvYr7sCwHbHcu4A5WwelxYK0GMJy37bNAarSJDFQsJ2ZvJjvMDmfWwDVFEVe_5tOomfVNt6bOg9mexbGjMrnHBnKnZR1vQbBtQieDlQepzTZMuQrSuKn-t5XZM7V6fCW7oP-uXGX-sMOajeX65JOf6XVpk29DP6ro8WTAflCDANC193yof8-f5_EYY-3hXhJj7RBXmizDpneEQDSaSz5sFk0sV5qPcARJ9zGG73vuGFyenjPPmtDtXtpx35A-BVcOSBYVIWe9kndG3nclfefjKEuZ3m4jL9Gg1h2JBvmXSMYiZtp9MR5I6pvbvylU_PP5xJFSjVTIz7IQSjcVGO41npnwIxRXNRxFOdIUHn0tjQ-7LwvEcTXyPsHXcMD8WtgBh-wxR8aKX7WPSsT1O8d8reb2aR7K3rkV3K82K_0OgawImEpwSvp9MNKynEAJQS6ZHe_J_l77652xwPNxMRTMASk1ZsJL",
                MFCountry.KUWAIT,
                MFEnvironment.TEST
            )

        } else {
            MFSDK.init("Put you Token API Key here", MFCountry.KUWAIT, MFEnvironment.LIVE)


        }
        // You can custom your action bar, but this is optional not required to set this line
        MFSDK.setUpActionBar(
            "BS NAS Payment", R.color.statusBarColor,
            R.color.statusBarColor, true
        )


        intentData?.price?.let { initiatePayment(it) }

        binding.textView100.text = intentData?.text1.toString()

        binding.textView75.text = intentData?.text2.toString()
        binding.textView76.text = intentData?.text3.toString()

        val userDob: Date = SimpleDateFormat("yyyy-MM-dd").parse(intentData?.startDate)
        val end: Date = SimpleDateFormat("yyyy-MM-dd").parse(intentData?.endDate)

        val diff: Long = userDob.time - end.time
        val numOfDays = (diff / (1000 * 60 * 60 * 24)).toInt()

        binding.textView79.text = "ساريه لمده$numOfDays يوم "


        binding.imageView59.setOnClickListener {
            intentData?.price?.let { it1 -> executePayment(1, it1) }

        }
        binding.imageView62.setOnClickListener {
            intentData?.price?.let { it1 -> executePayment(2, it1) }

        }
        binding.imageView63.setOnClickListener {
            intentData?.price?.let { it1 -> executePayment(2, it1) }

        }
        binding.textView87.text = intentData?.price.toString() + "K.D"

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

        MFSDK.executePayment(
            this,
            request,
            MFAPILanguage.AR,
            onInvoiceCreated = {
                Log.d(TAG, "invoiceId: $it")
            }
        ) { invoiceId: String, result: MFResult<MFGetPaymentStatusResponse> ->
            when (result) {
                is MFResult.Success -> {
                    Log.d(TAG, "Response: " + Gson().toJson(result.response))
                    showAlertDialog("Payment done successfully")
                    sendPayment(price)
                    binding.done.isVisible = true

                }
                is MFResult.Fail -> {
                    Log.d(TAG, "Fail: " + Gson().toJson(result.error))
                    showAlertDialog(Gson().toJson(result.error))

                }
            }

        }
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
            getLoginData().data.user.name, MFNotificationOption.LINK
        )
        request.customerEmail =
            getLoginData().data.user.email // The email required if you choose MFNotificationOption.ALL or MFNotificationOption.EMAIL
        request.customerMobile =
            getLoginData().data.user.phone // The mobile required if you choose MFNotificationOption.ALL or MFNotificationOption.SMS
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