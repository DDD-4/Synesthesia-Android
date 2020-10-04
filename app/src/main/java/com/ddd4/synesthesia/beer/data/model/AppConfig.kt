package com.ddd4.synesthesia.beer.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppConfig(
    @SerializedName("aroma_list")
    val aromaList: List<String>,
    @SerializedName("country_list")
    val countryList: List<String>,
    @SerializedName("style_list")
    val styleList: List<String>,
    @SerializedName("min_abv")
    val minAbv: Int,
    @SerializedName("max_abv")
    val maxAbv: Int
): Parcelable