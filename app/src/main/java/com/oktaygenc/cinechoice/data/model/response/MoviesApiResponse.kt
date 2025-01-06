package com.oktaygenc.cinechoice.data.model.response

import com.oktaygenc.cinechoice.data.model.entitiy.Movie

data class MoviesApiResponse(
    val movies: List<Movie>
)
