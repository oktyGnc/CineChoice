package com.oktaygenc.cinechoice.domain.model

data class CartItemModel(
    val cartIdList: List<Int>,
    val name: String,
    val image: String,
    val price: Int,
    val category: String,
    val rating: Double,
    val year: Int,
    val director: String,
    val description: String,
    val orderAmount: Int,
    val userName: String
)