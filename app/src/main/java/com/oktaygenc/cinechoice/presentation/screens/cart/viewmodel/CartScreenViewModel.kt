package com.oktaygenc.cinechoice.presentation.screens.cart.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.domain.model.CartItemModel
import com.oktaygenc.cinechoice.domain.usecase.DeleteMovieFromCartUseCase
import com.oktaygenc.cinechoice.domain.usecase.GetMoviesInCartUseCase
import com.oktaygenc.cinechoice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    // Injecting use cases for getting and deleting movies in the cart
    private val getMoviesInCartUseCase: GetMoviesInCartUseCase,
    private val deleteMovieFromCartUseCase: DeleteMovieFromCartUseCase,
) : ViewModel() {
    // StateFlow to hold the list of cart movies
    private val _cartMovies = MutableStateFlow<List<CartItemModel>>(emptyList())
    val cartMovies: StateFlow<List<CartItemModel>> get() = _cartMovies.asStateFlow()

    // StateFlow to track the loading status
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Initialize and fetch the movies in the cart
    init {
        getMoviesInCart()
    }

    // Function to fetch the movies in the cart
    fun getMoviesInCart() {
        viewModelScope.launch {
            _isLoading.value = true
            // Call the use case to get the movies
            when (val resource = getMoviesInCartUseCase.invoke()) {
                is Resource.Success -> _cartMovies.value =
                    resource.data  // On success, update the cart
                is Resource.Error -> {
                    // Log error if fetching the cart fails
                    Log.e(
                        "CartViewModel", "Error getting movies in cart: ${resource.message}"
                    )
                    _cartMovies.value = emptyList()  // Clear the cart if there's an error
                }

                else -> Unit  // No action for other resource states
            }
            _isLoading.value = false
        }
    }

    // Function to delete movies from the cart
    fun deleteMovieFromCart(cartIdList: List<Int>) {
        viewModelScope.launch {
            _isLoading.value = true
            // Iterate through the list of cart IDs and delete each movie
            cartIdList.forEach { cartId ->
                when (val resource = deleteMovieFromCartUseCase.invoke(cartId)) {
                    is Resource.Success -> Log.d(
                        "CartViewModel",
                        "Movie deleted from cart: ${resource.data}"
                    )

                    is Resource.Error -> Log.e(
                        "CartViewModel",
                        "Error deleting movie from cart: ${resource.message}"
                    )

                    else -> Unit
                }
                _isLoading.value = false
            }
            // After deleting, refresh the cart content
            getMoviesInCart()
        }
    }
}






