package com.oktaygenc.cinechoice.ui.presentation.favorite.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.data.datasource.auth.AuthDataSource
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import com.oktaygenc.cinechoice.data.repository.favorite.FavoriteRepository
import com.oktaygenc.cinechoice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val authDataSource: AuthDataSource
) : ViewModel() {
    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList()) // StateFlow to hold favorite movies
    val favoriteMovies: StateFlow<List<Movie>> = _favoriteMovies // Exposing favorite movies as StateFlow

    init {
        loadFavorites() // Load favorite movies when ViewModel is initialized
    }

    // Function to load favorite movies from repository
    fun loadFavorites() {
        viewModelScope.launch {
            val userId = authDataSource.getUserId() // Get the user ID from the data source
            when (val result = favoriteRepository.getFavoriteMovies(userId)) { // Fetch favorite movies from repository
                is Resource.Success -> {
                    Log.e("FavoriteViewModelGel", "Favorite movies loaded: ${result.data}") // Log successful load
                    _favoriteMovies.value = result.data ?: emptyList() // Update the state with loaded data
                }
                is Resource.Error -> {
                    Log.e("FavoriteViewModelHata", "Error loading favorite movies: ${result.message}") // Log error if loading fails
                }
                else -> Unit // Do nothing for other cases
            }
        }
    }

    // Function to remove a movie from favorites
    fun removeFavoriteMovie(movieId: Int) {
        viewModelScope.launch {
            val userId = authDataSource.getUserId() // Get the user ID from the data source
            favoriteRepository.removeFavoriteMovie(userId, movieId) // Call repository to remove the movie
            loadFavorites() // Reload favorite movies after removal
        }
    }
}



