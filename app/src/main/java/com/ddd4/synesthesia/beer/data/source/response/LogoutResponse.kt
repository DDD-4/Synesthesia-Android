package com.ddd4.synesthesia.beer.data.source.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName(value = "id")
    val id : Int
)