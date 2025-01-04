package com.oktaygenc.cinechoice.data.datasource

import com.oktaygenc.cinechoice.data.model.AddDeleteResponse
import com.oktaygenc.cinechoice.data.model.CartApiResponse
import com.oktaygenc.cinechoice.data.model.MoviesApiResponse
import com.oktaygenc.cinechoice.retrofit.MovieApi
import javax.inject.Inject

class MoviesDataSource @Inject constructor(
    private val movieApi: MovieApi,
) {
    suspend fun getAllMovies(): MoviesApiResponse = movieApi.getAllMovies()

    suspend fun addMovieToCart(
        name: String,
        image: String,
        price: Int,
        category: String,
        rating: Double,
        year: Int,
        director: String,
        description: String,
        orderAmount: Int
    ): AddDeleteResponse {
        return movieApi.addMovieToCart(
            name, image, price, category, rating, year,
            director, description, orderAmount
        )
    }

    suspend fun deleteMovieFromCart(cartId: Int): AddDeleteResponse {
        return movieApi.deleteMovieFromCart(cartId)
    }

    suspend fun getMoviesInCart(): CartApiResponse = movieApi.getMoviesInCart()
}