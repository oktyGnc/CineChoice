package com.oktaygenc.cinechoice.data.model.response

import com.google.gson.annotations.SerializedName
import com.oktaygenc.cinechoice.data.model.entitiy.CardItem

// Data class representing the response for the movie cart API
data class CartApiResponse(
    @SerializedName("movie_cart") val cartItems: List<CardItem>? = null,
    val success: Int? = null,
)

