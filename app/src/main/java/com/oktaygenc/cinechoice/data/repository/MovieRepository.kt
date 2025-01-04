package com.oktaygenc.cinechoice.data.repository

import com.oktaygenc.cinechoice.data.datasource.MoviesDataSource
import com.oktaygenc.cinechoice.data.model.CardItem
import com.oktaygenc.cinechoice.data.model.Movie
import com.oktaygenc.cinechoice.utils.Resource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val moviesDataSource: MoviesDataSource
) {
    suspend fun getAllMovies(): Resource<List<Movie>> {
        return try {
            val response = moviesDataSource.getAllMovies()
            Resource.Success(response.movies)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun addMovieToCart(
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
        return try {
            val response = moviesDataSource.addMovieToCart(
                name, image, price, category, rating, year,
                director, description, orderAmount
            )
            if (response.success == 1) {
                Resource.Success(response.message)
            } else {
                Resource.Error(response.message)
            }
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun deleteMovieFromCart(cartId: Int): Resource<String> {
        return try {
            val response = moviesDataSource.deleteMovieFromCart(cartId)
            if (response.success == 1) {
                Resource.Success(response.message)
            } else {
                Resource.Error(response.message)
            }
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun getMoviesInCart(): Resource<List<CardItem>> {
        return try {
            val response = moviesDataSource.getMoviesInCart()

            val filmCounts = response.cartItems.groupingBy { it.name }.eachCount()
            val mappedFilms = mutableListOf<CardItem>()

            filmCounts.keys.forEach { filmName ->
                val item = response.cartItems.find { it.name == filmName }
                item?.let {
                    mappedFilms.add(it.copy(orderAmount = filmCounts[filmName] ?: 0))
                }
            }

            Resource.Success(mappedFilms)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}