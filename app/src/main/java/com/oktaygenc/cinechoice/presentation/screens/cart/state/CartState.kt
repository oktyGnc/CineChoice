package com.oktaygenc.cinechoice.presentation.screens.cart.state

import com.oktaygenc.cinechoice.data.model.entitiy.CardItem

data class CartState(
    // List of movies in the cart, initially empty
    val cartMovies: List<CardItem> = emptyList(),

    // Loading state, true if data is being loaded, false otherwise
    val isLoading: Boolean = false,

    // Error message in case of failure, null by default
    val error: String? = null,

    // Success message to show after a successful operation, null by default
    val successMessage: String? = null,

    // Success status, typically used to track the success of an operation (e.g., 1 for success, 0 for failure)
    val success: Int = 0,
)




