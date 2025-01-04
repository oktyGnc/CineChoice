package com.oktaygenc.cinechoice.usecase

import com.oktaygenc.cinechoice.data.repository.MovieRepository
import com.oktaygenc.cinechoice.utils.Resource
import javax.inject.Inject

class AddMovieToCartUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(
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
        return repository.addMovieToCart(
            name, image, price, category, rating,
            year, director, description, orderAmount
        )
    }
}