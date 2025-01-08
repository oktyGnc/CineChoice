package com.oktaygenc.cinechoice.ui.presentation.detail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.data.datasource.auth.AuthDataSource
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import com.oktaygenc.cinechoice.data.repository.FavoriteRepository
import com.oktaygenc.cinechoice.usecase.AddMovieToCartUseCase
import com.oktaygenc.cinechoice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val addMovieToCartUseCase: AddMovieToCartUseCase,
    private val favoriteRepository: FavoriteRepository,
    private val authDataSource: AuthDataSource
) : ViewModel() {
    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMovies: StateFlow<List<Movie>> = _favoriteMovies

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    init {
        loadFavorites()
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
    fun loadFavorites() {
        viewModelScope.launch {
            val userId = authDataSource.getUserId()
            when (val result = favoriteRepository.getFavoriteMovies(userId)) {
                is Resource.Success -> {
                    Log.e("FavoriteViewModelGel", "Favorite movies loaded: ${result.data}")
                    _favoriteMovies.value = result.data ?: emptyList()
                }
                is Resource.Error -> {
                    Log.e("FavoriteViewModelHata", "Error loading favorite movies: ${result.message}")
                }
                else -> Unit
            }
        }
    }

    fun removeFavoriteMovie(movieId: Int) {
        viewModelScope.launch {
            val userId = authDataSource.getUserId()
            favoriteRepository.removeFavoriteMovie(userId, movieId)
            loadFavorites()
        }
    }

    fun checkFavoriteMovie(movieId: Int) {
        viewModelScope.launch {
            val userId = authDataSource.getUserId()
            when (val result = favoriteRepository.checkFavoriteMovie(userId, movieId)) {
                is Resource.Success -> {
                    _isFavorite.value = result.data ?: false
                }
                else -> _isFavorite.value = false
            }
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            if (_isFavorite.value) {
                removeFavoriteMovie(movie.id)
            } else {
                addToFavorites(movie)
            }
            _isFavorite.value = !_isFavorite.value
        }
    }
}