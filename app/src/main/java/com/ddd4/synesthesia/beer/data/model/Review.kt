package com.ddd4.synesthesia.beer.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Review(
    @SerializedName("ratio")
    val ratio: Float?,
    @SerializedName("created_at")
    val date: Date?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("beer")
    val beer: Beer?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("nickname")
    val nickname: String?
) : Parcelable {
    val createdAt : String?
        get() = SimpleDateFormat("YYYY. MM. dd").format(date)
}