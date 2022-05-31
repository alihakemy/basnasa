package com.market.utils

import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import androidx.core.util.PatternsCompat
import com.google.i18n.phonenumbers.PhoneNumberUtil



fun getIso(context:Context): String {
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