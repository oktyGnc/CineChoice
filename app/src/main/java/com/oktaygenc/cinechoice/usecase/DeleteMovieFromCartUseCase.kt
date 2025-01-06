package com.oktaygenc.cinechoice.usecase

import com.oktaygenc.cinechoice.data.repository.movie.MovieRepository
import com.oktaygenc.cinechoice.utils.Resource
import javax.inject.Inject

class DeleteMovieFromCartUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(cartId: Int): Resource<String> {
        return repository.deleteMovieFromCart(cartId)
    }
}