package com.market.data.repo

import android.util.Log
import com.market.data.models.*
import com.market.data.models.get.categories.Categories
import com.market.data.models.get.forgetpassword.GetForgetPassword
import com.market.data.models.get.getconfirenewpassword.GetConfirmNewPassword
import com.market.data.models.get.login.LoginResponse
import com.market.data.models.get.register.RegisterResponse
import com.market.data.models.get.tagetcomplet.TagetCompleteData
import com.market.data.models.get.verificationPhone.VerificationPhone
import com.market.data.services.Authentication
import com.market.utils.ResultState
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.converter.gson.GsonConverterFactory.create
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


    suspend fun forgetPassword(forgetPassword: ForgetPassword): ResultState<GetForgetPassword> {
        return try {

            val result = authentication.forgetPassword(forgetPassword)
            if (result.isSuccessful) {
                if (result.body()?.status.toString().equals("true")) {
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


    suspend fun confirmNewPassword(confirmNewPassword: ConfirmNewPassword): ResultState<GetConfirmNewPassword> {
        return try {

            val result = authentication.confirmNewPassword(confirmNewPassword)
            if (result.isSuccessful) {
                if (result.body()?.status.toString().equals("true")) {
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

    suspend fun getCategories(): ResultState<Categories> {
        return try {
            val result = authentication.categories()
            if (result.isSuccessful) {
                return ResultState.Success(result.body()!!)
            } else {
                ResultState.Error(result.body()?.status.toString())

            }

        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())

        }
    }

    suspend fun completeJoinTager(
        sendCompleteJoin: SendCompleteJoin,
        token: String,
        image: MultipartBody.Part
    ): ResultState<TagetCompleteData> {
        return try {


            val facebook_link: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                sendCompleteJoin.facebook_link
            )
            val category_id: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                sendCompleteJoin.category_id
            )
            val arrivaltime: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                sendCompleteJoin.arrivaltime
            )

            val instagram_link: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                sendCompleteJoin.instagram_link
            )

            val whatsapp_link: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                sendCompleteJoin.whatsapp_link
            )

            val snapchat_link: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                sendCompleteJoin.snapchat_link
            )

            val lat: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), sendCompleteJoin.lat)

            val long: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), sendCompleteJoin.long)

            val about: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                sendCompleteJoin.about
            )

            val whatsapp: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                sendCompleteJoin.whatsapp
            )
            val phone: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                sendCompleteJoin.phone
            )


            val result = authentication.completeTager(
                category_id = category_id,
                arrivaltime = arrivaltime,
                phone = phone,
                lat = lat,
                long = long,
                about = about,
                whatsapp = whatsapp,
                facebook_link = facebook_link,
                instagram_link = instagram_link,
                snapchat_link = snapchat_link,
                token = "Bearer $token",
                image = image,
                whatsapp_link = whatsapp_link
            )

            if (result.isSuccessful) {
                result.body()?.error?.let {
                    ResultState.Error(it)
                } ?: let {
                    if (result.body()?.status.toString().trim().toLowerCase().equals("true")) {
                        ResultState.Success(result.body()!!)
                    } else {
                        ResultState.Error(result.body()?.status.toString())
                    }
                }

            } else {

                ResultState.Error(result.body()?.error.toString())

            }

        } catch (e: Exception) {

            ResultState.Error(e.localizedMessage.toString())

        }
    }

}