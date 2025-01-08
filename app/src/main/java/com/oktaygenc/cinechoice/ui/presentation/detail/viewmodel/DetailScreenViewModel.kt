package com.oktaygenc.cinechoice.ui.presentation.detail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import com.oktaygenc.cinechoice.data.repository.FavoriteRepository
import com.oktaygenc.cinechoice.usecase.AddMovieToCartUseCase
import com.oktaygenc.cinechoice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val addMovieToCartUseCase: AddMovieToCartUseCase,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

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

    fun addToFavorites(movie: Movie) {
        viewModelScope.launch {
            when (val resource = favoriteRepository.addFavoriteMovie(movie)) {
                is Resource.Success -> {
                    Log.d("DetailScreenViewModel", "Movie added to favorites")
                }

                is Resource.Error -> {
                    Log.e("DetailScreenViewModel", "Error adding movie to favorites: ${resource.message}")
                }

                else -> Unit
            }
        }
    }
}