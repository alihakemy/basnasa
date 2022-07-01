package com.market.data.models.get.paymentPackages


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Package(

    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("product_num")
    val productNum: Int?,
    @SerializedName("ActivateCount")
    val ActivateCount: String?,
    @SerializedName("text1")
    val text1: String?,
    @SerializedName("text2")
    val text2: String?,
    @SerializedName("text3")
    val text3: String?,
    var selected:Boolean=false
):Parcelable