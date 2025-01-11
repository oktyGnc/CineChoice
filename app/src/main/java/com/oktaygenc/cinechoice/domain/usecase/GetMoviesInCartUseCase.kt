package com.oktaygenc.cinechoice.domain.usecase

import com.oktaygenc.cinechoice.data.repository.movie.MovieRepository
import com.oktaygenc.cinechoice.domain.model.CartItemModel
import com.oktaygenc.cinechoice.utils.Resource
import javax.inject.Inject

// Use case for fetching movies currently in the user's cart.
class GetMoviesInCartUseCase @Inject constructor(
    private val repository: MovieRepository, // Injects the repository to retrieve cart items.
) {
    // This function is invoked to fetch the list of movies in the cart.
    suspend operator fun invoke(): Resource<List<CartItemModel>> {
        // Calls the repository function to get the movies in the cart and returns the result.
        return repository.getMoviesInCart()
    }
}
