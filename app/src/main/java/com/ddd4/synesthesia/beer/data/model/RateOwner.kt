package com.ddd4.synesthesia.beer.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RateOwner(
    @SerializedName("beer_id")
    val beerID: Int,
    @SerializedName("ratio")
    val ratio: Double,
    @SerializedName("user_id")
    val userID: Int
) : Parcelable