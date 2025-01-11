package com.oktaygenc.cinechoice.data.model.entitiy

// Data class representing an item in the shopping cart
data class CardItem(
    val cartId: Int? = null,
    val name: String? = null,
    val image: String? = null,
    val price: Int? = null,
    val category: String? = null,
    val rating: Double? = null,
    val year: Int? = null,
    val director: String? = null,
    val description: String? = null,
    val orderAmount: Int? = null,
    val userName: String? = null,
)

