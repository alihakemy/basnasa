package com.market.data.models.get.productdetails


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Image(
    @SerializedName("imagePath")
    val imagePath: String?
): Parcelable