package com.oktaygenc.cinechoice.ui.presentation.cart.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.data.model.CardItem
import com.oktaygenc.cinechoice.usecase.AddMovieToCartUseCase
import com.oktaygenc.cinechoice.usecase.GetMoviesInCartUseCase
import com.oktaygenc.cinechoice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val getMoviesInCartUseCase: GetMoviesInCartUseCase,
    private val addMovieToCartUseCase: AddMovieToCartUseCase
) : ViewModel() {
    private val _cartMovies = MutableLiveData<List<CardItem>>()
    val cartMovies: LiveData<List<CardItem>> get() = _cartMovies

    init {
        getMoviesInCart() // Varsayılan kullanıcı
    }


    fun getMoviesInCart() {
        viewModelScope.launch {
            when (val resource = getMoviesInCartUseCase.invoke()) {
                is Resource.Success -> _cartMovies.value = resource.data
                is Resource.Error -> Log.e("CartViewModel", "Error getting movies in cart: ${resource.message}")
            }
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
            try {
                addMovieToCartUseCase.invoke(
                    name, image, price, category, rating, year,
                    director, description, orderAmount
                )
                getMoviesInCart() // Listeyi güncelle
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error adding movie: ${e.message}")
            }
        }
    }
}






