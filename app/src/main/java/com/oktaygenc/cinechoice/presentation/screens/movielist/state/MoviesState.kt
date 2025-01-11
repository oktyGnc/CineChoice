package com.oktaygenc.cinechoice.presentation.screens.movielist.state

import com.oktaygenc.cinechoice.data.model.entitiy.Movie


// Data class representing the state of the movies screen
data class MoviesState(
    // List of movies to display
    val movies: List<Movie> = emptyList(),

    // Boolean flag indicating whether the movies are being loaded
    val isLoading: Boolean = false,

    // Optional error message when an error occurs
    val error: String? = null,

    // Optional success message when an action is successful
    val successMessage: String? = null,

    // The currently selected movie category (default is "All")
    val selectedCategory: String = "All",

    // List of movies filtered based on the selected category
    val filteredMovies: List<Movie> = emptyList(),
)
