package com.oktaygenc.cinechoice.ui.presentation.favorite.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.data.datasource.auth.AuthDataSource
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import com.oktaygenc.cinechoice.data.repository.FavoriteRepository
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
    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMovies: StateFlow<List<Movie>> = _favoriteMovies

    init {
        loadFavorites()
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
}


