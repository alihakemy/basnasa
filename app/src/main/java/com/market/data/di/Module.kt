package com.market.data.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.market.BuildConfig
import com.market.data.repo.AuthenticationRepository
import com.market.data.repo.OnBoardingRepository
import com.market.data.repo.UserRepoImp
import com.market.data.services.Authentication
import com.market.data.services.OnBoardingServices
import com.market.data.services.apis
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

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

        return OkHttpClient.Builder()

            .connectTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(Interceptors(sharedPreferences, lang))

            .addInterceptor(ChuckerInterceptor(appContext))


            .build()
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
    fun getUserRepoImp(user: apis): UserRepoImp {
        return UserRepoImp(user)
    }


    @Provides
    fun getAuthentication(retrofit: Retrofit): Authentication {
        return retrofit.create(Authentication::class.java)
    }

    @Provides
    fun getUserServices(retrofit: Retrofit): apis {
        return retrofit.create(apis::class.java)
    }

}