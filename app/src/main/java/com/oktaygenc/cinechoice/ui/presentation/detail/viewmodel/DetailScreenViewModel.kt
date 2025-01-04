package com.oktaygenc.cinechoice.ui.presentation.detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.data.model.CardItem
import com.oktaygenc.cinechoice.data.model.Movie
import com.oktaygenc.cinechoice.usecase.AddMovieToCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private val addMovieToCartUseCase: AddMovieToCartUseCase) :
    ViewModel() {
    private val _cartMovies = MutableLiveData<List<Movie>>()
    val cartMovies: LiveData<List<Movie>> get() = _cartMovies
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
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error adding movie: ${e.message}")
            }
        }
    }
}