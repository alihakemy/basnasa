package com.market.presentation.bases

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate
import com.akexorcist.localizationactivity.core.OnLocaleChangedListener
import com.google.gson.Gson
import com.market.data.models.get.User
import com.market.data.models.get.login.LoginResponse
import com.market.presentation.bases.methods.BaseMethods
import com.market.presentation.bases.methods.LoginData
import java.util.*


abstract class BaseActivity : AppCompatActivity() , OnLocaleChangedListener {

    private val viewModel: BaseViewModel by viewModels()
    private val localizationDelegate = LocalizationActivityDelegate(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        localizationDelegate.addOnLocaleChangedListener(this)
        localizationDelegate.onCreate()

        super.onCreate(savedInstanceState)
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        setLanguage(preferences.getString("lang", "en"))
    }



    fun checkOnboard(): Boolean {
        return viewModel.checkOnboard()
    }

    fun checkIsLogin(): Boolean {
        return viewModel.checkIsLogin()
    }

    fun getLoginData():LoginResponse {
        return viewModel.getLoginData()
    }

    fun getLocation(): Boolean {
        return viewModel.getLocation()
    }

    fun getLatLong(): Pair<String, String> {
        return viewModel.getLatLong()
    }

    fun storeLoginData(user: LoginResponse){
        viewModel.storeLoginData(user)
    }
    public override fun onResume() {
        super.onResume()
        localizationDelegate.onResume(this)
    }

    override fun attachBaseContext(newBase: Context) {
        applyOverrideConfiguration(localizationDelegate.updateConfigurationLocale(newBase))
        super.attachBaseContext(newBase)
    }

    override fun getApplicationContext(): Context {
        return localizationDelegate.getApplicationContext(super.getApplicationContext())
    }

    override fun getResources(): Resources {
        return localizationDelegate.getResources(super.getResources())
    }

    fun setLanguage(language: String?) {
        localizationDelegate.setLanguage(this, language!!)
    }

    fun setLanguage(locale: Locale?) {
        localizationDelegate.setLanguage(this, locale!!)
    }

    val currentLanguage: Locale
        get() = localizationDelegate.getLanguage(this)

    // Just override method locale change event
    override fun onBeforeLocaleChanged() {}
    override fun onAfterLocaleChanged() {}

}