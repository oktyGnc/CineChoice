package com.oktaygenc.cinechoice.usecase

import com.oktaygenc.cinechoice.data.model.Movie
import com.oktaygenc.cinechoice.data.repository.MovieRepository
import com.oktaygenc.cinechoice.utils.Resource
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(
private val repository: MovieRepository
) {
    suspend operator fun invoke(): Resource<List<Movie>> {
        return repository.getAllMovies()
    }
}