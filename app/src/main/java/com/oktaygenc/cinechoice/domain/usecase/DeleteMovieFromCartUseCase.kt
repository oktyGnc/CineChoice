package com.oktaygenc.cinechoice.domain.usecase

import com.oktaygenc.cinechoice.data.repository.movie.MovieRepository
import com.oktaygenc.cinechoice.utils.Resource
import javax.inject.Inject

// Use case for deleting a movie from the cart.
class DeleteMovieFromCartUseCase @Inject constructor(
    private val repository: MovieRepository, // Injects the repository to interact with movie data.
) {
    // This function is invoked to delete a movie from the cart using its cartId.
    suspend operator fun invoke(cartId: Int): Resource<String> {
        // Calls the repository function to delete the movie from the cart and returns the result.
        return repository.deleteMovieFromCart(cartId)
    }
}
