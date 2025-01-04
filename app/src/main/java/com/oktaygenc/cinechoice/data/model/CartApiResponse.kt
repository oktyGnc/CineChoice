package com.oktaygenc.cinechoice.data.model

import com.google.gson.annotations.SerializedName

data class CartApiResponse(
    @SerializedName("movie_cart") val cartItems: List<CardItem>,
    val success: Int
)
