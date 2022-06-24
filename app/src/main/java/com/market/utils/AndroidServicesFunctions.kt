package com.market.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.util.PatternsCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.i18n.phonenumbers.PhoneNumberUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


fun isAtLeastVersion(version: Int): Boolean {
    return Build.VERSION.SDK_INT >= version
}

fun startLink(link: String, context: Context) {

    try {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        context.startActivity(browserIntent)

    }catch (E:Exception){

        Toast.makeText(context,"Link Error",Toast.LENGTH_LONG).show()
    }
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            t?.let {
                removeObserver(this)
            }

        }
    })
}

fun String.toRequestBody(): RequestBody {
   return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), this)

}
fun prepareFilePart(partName: String, fileUri: String): MultipartBody.Part {

    val file: File = File(fileUri)

    // create RequestBody instance from file
    val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)

    // MultipartBody.Part is used to send also the actual file name
    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}

fun getIso(context: Context): String {
    return try {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val countryCode = telephonyManager.simCountryIso.toUpperCase()
        PhoneNumberUtil.getInstance().getCountryCodeForRegion(countryCode).toString()
    } catch (e: Exception) {
        "+965"
    }
}

fun String.isValidEmail(): Boolean {
    return PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()
}

fun Intent.clearStack() {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
}