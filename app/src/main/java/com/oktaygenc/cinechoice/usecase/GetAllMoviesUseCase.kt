package com.oktaygenc.cinechoice.usecase

import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import com.oktaygenc.cinechoice.data.repository.movie.MovieRepository
import com.oktaygenc.cinechoice.utils.Resource
import javax.inject.Inject

// Use case for fetching all movies.
class GetAllMoviesUseCase @Inject constructor(
    private val repository: MovieRepository // Injects the repository to retrieve movie data.
) {
    // This function is invoked to fetch the list of all movies from the repository.
    suspend operator fun invoke(): Resource<List<Movie>> {
        // Calls the repository function to get all movies and returns the result.
        return repository.getAllMovies()
    }
}
