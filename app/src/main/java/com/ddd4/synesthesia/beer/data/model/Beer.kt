package com.ddd4.synesthesia.beer.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Beer(
    @SerializedName("abv")
    val abv: Double? = null,
    @SerializedName("aroma")
    val aromas: List<String>? = null,
    @SerializedName("beer_style")
    val beerStyle: String? = null,
    @SerializedName("brewery")
    val brewery: String? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: List<String>? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("rate_avg")
    val rateAvg: Double? = null,
    @SerializedName("rate_owner")
    val rateOwner: RateOwner? = null,
    @SerializedName("reviews")
    val reviews: List<Review>? = null,
    @SerializedName("thumbnail_image")
    val thumbnailImage : String? = null,
    @SerializedName("review_owner")
    val reviewOwner : ReviewOwner? = null
) : Parcelable