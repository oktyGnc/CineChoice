package com.oktaygenc.cinechoice.ui.presentation.movielist.state

import com.oktaygenc.cinechoice.data.model.Movie

sealed interface MoviesAction {
    data class OnCartClick(val movie: Movie) : MoviesAction
    data class OnCategoryClick(val category: String) : MoviesAction
}