package com.ddd4.synesthesia.beer.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Beer(
    @SerializedName("abv")
    val abv: Double,
    @SerializedName("aroma")
    val aromas: List<String>,
    @SerializedName("beer_style")
    val beerStyle: String,
    @SerializedName("brewery")
    val brewery: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("rate_avg")
    val rateAvg: Double,
    @SerializedName("rate_owner")
    val rateOwner: RateOwner,
    @SerializedName("reviews")
    val reviews: List<Review>?,
    @SerializedName("thumbnail_image")
    val thumbnailImage : String?,
    @SerializedName("review_owner")
    val reviewOwner : ReviewOwner?
) : Parcelable