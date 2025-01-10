package com.oktaygenc.cinechoice.data.model.response

import com.google.gson.annotations.SerializedName
import com.oktaygenc.cinechoice.data.model.entitiy.CardItem

data class CartApiResponse(
    @SerializedName("movie_cart") val cartItems: List<CardItem>? = null,
    val success: Int? = null,
)
