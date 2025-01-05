package com.oktaygenc.cinechoice.ui.presentation.cart.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.data.model.CardItem
import com.oktaygenc.cinechoice.usecase.AddMovieToCartUseCase
import com.oktaygenc.cinechoice.usecase.DeleteMovieFromCartUseCase
import com.oktaygenc.cinechoice.usecase.GetMoviesInCartUseCase
import com.oktaygenc.cinechoice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val getMoviesInCartUseCase: GetMoviesInCartUseCase,
    private val addMovieToCartUseCase: AddMovieToCartUseCase,
    private val deleteMovieFromCartUseCase: DeleteMovieFromCartUseCase,
) : ViewModel() {
    private val _cartMovies = MutableLiveData<List<CardItem>>()
    val cartMovies: LiveData<List<CardItem>> get() = _cartMovies

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()




    init {
        getMoviesInCart() // Varsayılan kullanıcı
    }

    fun getMoviesInCart() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val resource = getMoviesInCartUseCase.invoke()) {
                is Resource.Success -> _cartMovies.value = resource.data
                is Resource.Error -> Log.e(
                    "CartViewModel", "Error getting movies in cart: ${resource.message}"
                )
            }
            _isLoading.value = false
        }
    }

    fun deleteMovieFromCart(cartId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _cartMovies.value = cartMovies.value?.filter { it.cartId != cartId }
            when (val resource = deleteMovieFromCartUseCase.invoke(cartId)) {
                is Resource.Success -> {
                    getMoviesInCart()
                }

                is Resource.Error -> {
                    Log.e("CartViewModel", "Error deleting movie from cart: ${resource.message}")
                    getMoviesInCart()
                }
            }
            _isLoading.value = false
        }
    }

    fun addMovieToCart(
        name: String,
        image: String,
        price: Int,
        category: String,
        rating: Double,
        year: Int,
        director: String,
        description: String,
        orderAmount: Int,
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                addMovieToCartUseCase.invoke(
                    name, image, price, category, rating, year, director, description, orderAmount
                )
                getMoviesInCart() // Listeyi güncelle
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error adding movie: ${e.message}")
            }
            _isLoading.value = false
        }
    }


}






