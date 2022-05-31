package com.market.data.services

import com.market.data.models.*
import com.market.data.models.get.forgetpassword.GetForgetPassword
import com.market.data.models.get.getconfirenewpassword.GetConfirmNewPassword
import com.market.data.models.get.login.LoginResponse
import com.market.data.models.get.register.RegisterResponse
import com.market.data.models.get.verificationPhone.VerificationPhone
import retrofit2.Response
import retrofit2.http.*

interface Authentication {

    @POST("api/login")
    suspend fun login(@Body sendLogin: SendLogin):Response<LoginResponse>


    @POST("api/register")
    suspend fun register( @Body sendRegister: SendRegister):Response<RegisterResponse>



    @POST("api/verificationPhone")
    suspend fun verificationPhone(

        @Body verificationPhone: SendVerificationPhone
    ):Response<VerificationPhone>




    @POST("api/resend")
    suspend fun resendCode( @Body resendCode: ResendCode)


    @POST("api/forgetPassword")
    suspend fun forgetPassword(

        @Body forgetPassword: ForgetPassword
    ):Response<GetForgetPassword>

    @POST("api/confirmNewPassword")
    suspend fun confirmNewPassword(

        @Body confirmNewPassword: ConfirmNewPassword
    ):Response<GetConfirmNewPassword>


}