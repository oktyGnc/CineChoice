package com.oktaygenc.cinechoice.ui.presentation.movielist.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.ui.presentation.movielist.state.MoviesAction
import com.oktaygenc.cinechoice.ui.presentation.movielist.state.MoviesState
import com.oktaygenc.cinechoice.usecase.AddMovieToCartUseCase
import com.oktaygenc.cinechoice.usecase.GetAllMoviesUseCase
import com.oktaygenc.cinechoice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListScreenViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val addMovieToCartUseCase: AddMovieToCartUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(MoviesState())
    val state: State<MoviesState> = _state

    init {
        loadMovies()
    }

    fun onAction(action: MoviesAction) {
        when (action) {
            is MoviesAction.OnCartClick -> addMovieToCart(
                action.movie.name, action.movie.image, action.movie.price,
                action.movie.category, action.movie.rating, action.movie.year,
                action.movie.director, action.movie.description, 1
            )

            is MoviesAction.OnCategoryClick -> {
                if (action.category == "All") {
                    updateUiState { copy(filteredMovies = state.value.movies) }
                } else {
                    val list = state.value.movies.filter { it.category == action.category }
                    updateUiState { copy(filteredMovies = list) }
                }
            }
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            when (val resource = getAllMoviesUseCase()) {
                is Resource.Success -> updateUiState {
                    copy(
                        movies = resource.data,
                        filteredMovies = resource.data,
                        isLoading = false
                    )
                }

                is Resource.Error -> updateUiState { copy(error = resource.message, isLoading = false) }
                Resource.Empty -> TODO()
                Resource.Loading -> TODO()
            }
        }
    }

    private fun addMovieToCart(
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
            val resource = addMovieToCartUseCase(
                name, image, price, category, rating, year, director,
                description, orderAmount
            )

            when (resource) {
                is Resource.Success -> updateUiState { copy(successMessage = resource.data) }
                is Resource.Error -> updateUiState { copy(error = resource.message) }
                Resource.Empty -> TODO()
                Resource.Loading -> TODO()
            }
        }
    }

    private fun updateUiState(block: MoviesState.() -> MoviesState) {
        _state.value = block(_state.value)
    }
}
