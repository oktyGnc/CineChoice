package com.oktaygenc.cinechoice.data.model.response

import com.oktaygenc.cinechoice.data.model.entitiy.Movie

// Data class representing the response for the movies API
data class MoviesApiResponse(
    val movies: List<Movie>
)
