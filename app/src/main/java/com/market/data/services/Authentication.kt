package com.market.data.services

import com.market.data.models.*
import com.market.data.models.get.categories.Categories
import com.market.data.models.get.forgetpassword.GetForgetPassword
import com.market.data.models.get.getconfirenewpassword.GetConfirmNewPassword
import com.market.data.models.get.login.LoginResponse
import com.market.data.models.get.register.RegisterResponse
import com.market.data.models.get.tagetcomplet.TagetCompleteData
import com.market.data.models.get.verificationPhone.VerificationPhone
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Authentication {

    @POST("api/login")
    suspend fun login(@Body sendLogin: SendLogin): Response<LoginResponse>


    @POST("api/register")
    suspend fun register(@Body sendRegister: SendRegister): Response<RegisterResponse>


    @POST("api/verificationPhone")
    suspend fun verificationPhone(

        @Body verificationPhone: SendVerificationPhone
    ): Response<VerificationPhone>


    @POST("api/resend")
    suspend fun resendCode(@Body resendCode: ResendCode)


    @POST("api/forgetPassword")
    suspend fun forgetPassword(

        @Body forgetPassword: ForgetPassword
    ): Response<GetForgetPassword>

    @POST("api/confirmNewPassword")
    suspend fun confirmNewPassword(

        @Body confirmNewPassword: ConfirmNewPassword
    ): Response<GetConfirmNewPassword>


    @GET("api/categories")
    suspend fun categories(): Response<Categories>




    @Multipart
    @POST("api/completeTager")
    suspend fun completeTager(
        @Part("category_id") category_id: RequestBody,
        @Part("arrivaltime") arrivaltime: RequestBody,
        @Part("instagram_link") instagram_link: RequestBody,
        @Part("facebook_link") facebook_link: RequestBody,
        @Part("whatsapp_link") whatsapp_link: RequestBody,
        @Part("snapchat_link") snapchat_link: RequestBody,
        @Part("lat ") lat: RequestBody,
        @Part("long") long: RequestBody,
        @Part("about") about: RequestBody,

        @Part("phone ") phone: RequestBody,
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Response<TagetCompleteData>


}