package com.ddd4.synesthesia.beer.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Related(
    @SerializedName("abv")
    val abv: Double,
    @SerializedName("aroma")
    val aroma: List<String>,
    @SerializedName("beer_style")
    val beerStyle: String,
    @SerializedName("brewery")
    val brewery: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("rate_avg")
    val rateAvg: Double,
    @SerializedName("thumbnail_image")
    val thumbnailImage: String

) : Parcelable