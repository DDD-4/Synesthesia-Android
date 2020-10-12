package com.ddd4.synesthesia.beer.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewOwner(
    @SerializedName("beer")
    val beer: Beer?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("ratio")
    val ratio: Float? = 0.5f,
    @SerializedName("user_id")
    val userId: Int
) : Parcelable