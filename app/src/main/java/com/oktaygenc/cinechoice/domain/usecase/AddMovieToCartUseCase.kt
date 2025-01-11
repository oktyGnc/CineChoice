package com.oktaygenc.cinechoice.domain.usecase

import com.oktaygenc.cinechoice.data.repository.movie.MovieRepository
import com.oktaygenc.cinechoice.utils.Resource
import javax.inject.Inject

// Use case for adding a movie to the cart.
class AddMovieToCartUseCase @Inject constructor(
    private val repository: MovieRepository, // Injects the repository to interact with movie data.
) {
    // This function is invoked to add a movie to the cart.
    suspend operator fun invoke(
        // Parameters for movie details.
        name: String,
        image: String,
        price: Int,
        category: String,
        rating: Double,
        year: Int,
        director: String,
        description: String,
        orderAmount: Int,
    ): Resource<String> {
        // Calls the repository function to add the movie to the cart and returns the result.
        return repository.addMovieToCart(
            name, image, price, category, rating, year, director, description, orderAmount
        )
    }
}
