package com.oktaygenc.cinechoice.data.repository.movie

import com.oktaygenc.cinechoice.data.datasource.movies.MoviesDataSource
import com.oktaygenc.cinechoice.data.model.entitiy.CardItem
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
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

            // Aynı isimdeki filmlerin orderAmount'larını topla
            val groupedFilms = response.cartItems.groupBy { it.name }
            val mergedFilms = groupedFilms.map { (_, films) ->
                val firstFilm = films.first()
                val totalAmount = films.sumOf { it.orderAmount }
                firstFilm.copy(orderAmount = totalAmount)
            }

            Resource.Success(mergedFilms)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}