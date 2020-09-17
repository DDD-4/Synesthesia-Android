package com.ddd4.synesthesia.beer.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review(
    @SerializedName("name")
    val name: String,
    @SerializedName("rate")
    val rate: Float,
    @SerializedName("date")
    val date: String,
    @SerializedName("review")
    val review: String
) : Parcelable