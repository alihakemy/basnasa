package com.market.data.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.market.BuildConfig
import com.market.data.models.get.login.LoginResponse
import com.market.data.repo.AuthenticationRepository
import com.market.data.repo.OnBoardingRepository
import com.market.data.repo.UserRepoImp
import com.market.data.services.Authentication
import com.market.data.services.OnBoardingServices
import com.market.data.services.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.math.ln

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideDefaultSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(appContext)
    }

    @Provides
    @Singleton
    fun setupOkHttp(
        @ApplicationContext appContext: Context,
        sharedPreferences: SharedPreferences
    ): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val lang = if (Locale.getDefault().displayLanguage.toLowerCase().contains("e")) {
            "en"
        } else {
            "ar"
        }
        Log.d("TAG", "getDisplayLanguage = " + Locale.getDefault().displayLanguage);

        return OkHttpClient.Builder()

            .connectTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(Interceptors(getToken(sharedPreferences), lang))

            .addInterceptor(ChuckerInterceptor(appContext))


            .build()
    }


    private fun getToken(sharedPreferences: SharedPreferences): String {
        val gson = Gson()

        val loginresponse = gson.fromJson(
            sharedPreferences.getString("loginData", "").toString(),
            LoginResponse::class.java
        )
        return loginresponse.data.token.toString()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        val baseUrl = BuildConfig.BaseUrl
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)

            .addConverterFactory(gsonConverterFactory).build()
    }


    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun getOnBoarding(retrofit: Retrofit): OnBoardingServices {
        return retrofit.create(OnBoardingServices::class.java)
    }

    @Provides
    fun getOnBoardingRepository(onBoardingServices: OnBoardingServices): OnBoardingRepository {
        return OnBoardingRepository(onBoardingServices)
    }

    @Provides
    fun getAuthenticationRepository(authentication: Authentication): AuthenticationRepository {
        return AuthenticationRepository(authentication)
    }

    @Provides
    fun getUserRepoImp(user: User): UserRepoImp {
        return UserRepoImp(user)
    }


    @Provides
    fun getAuthentication(retrofit: Retrofit): Authentication {
        return retrofit.create(Authentication::class.java)
    }

    @Provides
    fun getUserServices(retrofit: Retrofit): User {
        return retrofit.create(User::class.java)
    }

}