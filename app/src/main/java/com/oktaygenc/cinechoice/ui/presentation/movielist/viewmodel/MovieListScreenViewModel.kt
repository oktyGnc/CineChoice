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

// ViewModel class for managing the Movie List screen logic
@HiltViewModel
class MovieListScreenViewModel @Inject constructor(
    // Use cases for fetching movies and adding movies to cart
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val addMovieToCartUseCase: AddMovieToCartUseCase,
) : ViewModel() {

    // Internal state of the screen encapsulated in mutable state
    private val _state = mutableStateOf(MoviesState())

    // Public state exposed to the UI
    val state: State<MoviesState> = _state

    // Initialization block to load movies when the ViewModel is created
    init {
        loadMovies()
    }

    // Function to handle different actions (e.g., adding movie to cart or category selection)
    fun onAction(action: MoviesAction) {
        when (action) {
            // Handling the action when a movie is added to the cart
            is MoviesAction.OnCartClick -> addMovieToCart(
                action.movie.name,
                action.movie.image,
                action.movie.price,
                action.movie.category,
                action.movie.rating,
                action.movie.year,
                action.movie.director,
                action.movie.description,
                1
            )

            // Handling the action when a category is selected
            is MoviesAction.OnCategoryClick -> {
                if (action.category == "All") {
                    // If the selected category is "All", show all movies
                    updateUiState { copy(filteredMovies = state.value.movies) }
                } else {
                    // Filter movies based on the selected category
                    val list = state.value.movies.filter { it.category == action.category }
                    updateUiState { copy(filteredMovies = list) }
                }
            }
        }
    }

    // Function to load all movies from the use case
    private fun loadMovies() {
        viewModelScope.launch {
            when (val resource = getAllMoviesUseCase()) {
                is Resource.Success -> updateUiState {
                    // If the movies are successfully fetched, update the UI state with movies data
                    copy(
                        movies = resource.data, filteredMovies = resource.data, isLoading = false
                    )
                }

                // If there is an error, update the UI state with the error message
                is Resource.Error -> updateUiState {
                    copy(
                        error = resource.message,
                        isLoading = false
                    )
                }

                // Placeholder for handling empty or loading states
                Resource.Empty -> TODO()
                Resource.Loading -> TODO()
            }
        }
    }

    // Function to add a movie to the cart
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
                name, image, price, category, rating, year, director, description, orderAmount
            )

            when (resource) {
                is Resource.Success -> updateUiState { copy(successMessage = resource.data) }
                is Resource.Error -> updateUiState { copy(error = resource.message) }
                Resource.Empty -> TODO()
                Resource.Loading -> TODO()
            }
        }
    }

    // Helper function to update the UI state
    private fun updateUiState(block: MoviesState.() -> MoviesState) {
        _state.value = block(_state.value)
    }
}
