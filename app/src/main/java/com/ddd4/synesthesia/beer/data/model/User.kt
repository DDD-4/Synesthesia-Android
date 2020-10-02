package com.ddd4.synesthesia.beer.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id : String,
    @SerializedName("external_id")
    val externalId : String,
    @SerializedName("nickname")
    val nickname : String,
    @SerializedName("profile_image")
    val profileImage : String,
    @SerializedName("thumbnail_image")
    val thumbnailImage : String
)