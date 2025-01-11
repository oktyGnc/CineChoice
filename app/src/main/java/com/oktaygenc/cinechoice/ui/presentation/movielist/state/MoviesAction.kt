package com.oktaygenc.cinechoice.ui.presentation.movielist.state

import com.oktaygenc.cinechoice.data.model.entitiy.Movie

// Sealed interface to represent different actions for movies
sealed interface MoviesAction {

    // Action when a movie is clicked to add to the cart
    data class OnCartClick(val movie: Movie) : MoviesAction

    // Action when a category is selected
    data class OnCategoryClick(val category: String) : MoviesAction
}
