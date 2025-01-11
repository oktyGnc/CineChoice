package com.oktaygenc.cinechoice.data.datasource.movies

import com.oktaygenc.cinechoice.data.model.response.AddDeleteResponse
import com.oktaygenc.cinechoice.data.model.response.CartApiResponse
import com.oktaygenc.cinechoice.data.model.response.MoviesApiResponse
import com.oktaygenc.cinechoice.retrofit.MovieApi
import javax.inject.Inject

class MoviesDataSource @Inject constructor(
    private val movieApi: MovieApi,
) {
    // Fetches a list of all movies from the API
    suspend fun getAllMovies(): MoviesApiResponse = movieApi.getAllMovies()

    // Adds a movie to the user's shopping cart using various movie details
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

    // Deletes a movie from the user's shopping cart using the cart ID
    suspend fun deleteMovieFromCart(cartId: Int): AddDeleteResponse {
        return movieApi.deleteMovieFromCart(cartId)
    }

    // Retrieves the list of movies currently in the user's shopping cart
    suspend fun getMoviesInCart(): CartApiResponse = movieApi.getMoviesInCart()
}
