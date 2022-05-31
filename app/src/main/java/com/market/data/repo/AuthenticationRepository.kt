package com.market.data.repo

import android.content.SharedPreferences
import com.market.data.models.get.OnBoardingGet
import com.market.data.models.get.login.LoginResponse
import com.market.data.models.get.register.RegisterResponse
import com.market.data.services.Authentication
import com.market.data.services.OnBoardingServices
import com.market.base.utils.ResultState
import com.market.data.models.*
import com.market.data.models.get.forgetpassword.GetForgetPassword
import com.market.data.models.get.getconfirenewpassword.GetConfirmNewPassword
import com.market.data.models.get.verificationPhone.VerificationPhone
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val authentication: Authentication,

    ) {


    suspend fun login(sendLogin: SendLogin): ResultState<LoginResponse> {
        return try {

            val result = authentication.login(sendLogin)
            if (result.isSuccessful) {
                if (result.body()?.status.equals("true")) {
                    ResultState.Success(result.body()!!)
                } else {
                    ResultState.Error(result.body()?.message.toString())
                }
            } else {
                ResultState.Error(result.message().toString())
            }


        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())
        }
    }


    suspend fun register(register: SendRegister): ResultState<RegisterResponse> {
        return try {

            val result = authentication.register(register)
            if (result.isSuccessful) {
                ResultState.Success(result.body()!!)

            } else {
                ResultState.Error(result.message().toString())
            }


        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())
        }
    }


    suspend fun verifyCode(verificationPhone: SendVerificationPhone): ResultState<VerificationPhone> {
        return try {

            val result = authentication.verificationPhone(verificationPhone)
            if (result.isSuccessful) {
                if (result.body()?.status.toString().equals("true")) {
                    ResultState.Success(result.body()!!)
                }else
                {
                    ResultState.Error(result.body()?.message.toString())

                }

            } else {
                ResultState.Error(result.message().toString())
            }


        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())
        }
    }


    suspend fun forgetPassword(forgetPassword: ForgetPassword): ResultState<GetForgetPassword> {
        return try {

            val result = authentication.forgetPassword(forgetPassword)
            if (result.isSuccessful) {
                if (result.body()?.status.toString().equals("true")) {
                    ResultState.Success(result.body()!!)
                }else
                {
                    ResultState.Error(result.body()?.message.toString())

                }

            } else {
                ResultState.Error(result.message().toString())
            }


        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())
        }
    }


    suspend fun confirmNewPassword(confirmNewPassword: ConfirmNewPassword): ResultState<GetConfirmNewPassword> {
        return try {

            val result = authentication.confirmNewPassword(confirmNewPassword)
            if (result.isSuccessful) {
                if (result.body()?.status.toString().equals("true")) {
                    ResultState.Success(result.body()!!)
                }else
                {
                    ResultState.Error(result.body()?.message.toString())

                }

            } else {
                ResultState.Error(result.message().toString())
            }


        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())
        }
    }

}