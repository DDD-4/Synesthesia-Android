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
    @SerializedName("comments")
    val comments: List<Comment>,
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
    val reviews: List<Review>? = null
) : Parcelable {
    val rateAvgToString: String
        get() = rateAvg.toString()

    val abvToString: String
        get() = abv.toString()
}