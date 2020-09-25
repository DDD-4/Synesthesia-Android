package com.ddd4.synesthesia.beer.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RelatedBeers(
    @SerializedName("aroma_related")
    val aromaRelated: List<Related>?,
    @SerializedName("randomly_related")
    val randomlyRelated: List<Related>?,
    @SerializedName("style_related")
    val styleRelated: List<Related>?
) : Parcelable