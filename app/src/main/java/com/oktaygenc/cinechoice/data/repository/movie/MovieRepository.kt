package com.oktaygenc.cinechoice.data.repository.movie

import com.oktaygenc.cinechoice.data.datasource.movies.MoviesDataSource
import com.oktaygenc.cinechoice.data.mapper.toModel
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import com.oktaygenc.cinechoice.domain.model.CartItemModel
import com.oktaygenc.cinechoice.utils.Resource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val moviesDataSource: MoviesDataSource, // Injecting MoviesDataSource to interact with the movie API
) {
    // Function to get all movies from the data source
    suspend fun getAllMovies(): Resource<List<Movie>> {
        return try {
            // Fetch all movies from the MoviesDataSource
            val response = moviesDataSource.getAllMovies()
            Resource.Success(response.movies) // Return the list of movies if successful
        } catch (e: Exception) {
            Resource.Error(e.message.toString()) // Return error if an exception occurs
        }
    }

    // Function to add a movie to the cart
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
            // Send the movie data to the MoviesDataSource to add to the cart
            val response = moviesDataSource.addMovieToCart(
                name, image, price, category, rating, year, director, description, orderAmount
            )
            // Check the response and return appropriate success or error message
            if (response.success == 1) {
                Resource.Success(response.message) // Return success message if the movie is added successfully
            } else {
                Resource.Error(response.message) // Return error message if the operation failed
            }
        } catch (e: Exception) {
            Resource.Error(e.message.toString()) // Return error if an exception occurs
        }
    }

    // Function to delete a movie from the cart
    suspend fun deleteMovieFromCart(cartId: Int): Resource<String> {
        return try {
            // Send request to remove the movie from the cart
            val response = moviesDataSource.deleteMovieFromCart(cartId)
            // Check the response and return appropriate success or error message
            if (response.success == 1) {
                Resource.Success(response.message) // Return success message if the movie is deleted successfully
            } else {
                Resource.Error(response.message) // Return error message if the operation failed
            }
        } catch (e: Exception) {
            Resource.Error(e.message.toString()) // Return error if an exception occurs
        }
    }

    // Function to get movies currently in the cart
    suspend fun getMoviesInCart(): Resource<List<CartItemModel>> {
        return try {
            // Fetch the list of cart items from the data source
            val response = moviesDataSource.getMoviesInCart()
            // Group the cart items by movie name and merge duplicates
            val groupedFilms = response.cartItems?.groupBy { it.name }
            val mergedFilms = groupedFilms?.map { (_, films) ->
                val firstFilm = films.first().toModel() // Get the first film's data
                val totalAmount = films.sumOf { it.orderAmount ?: 0 } // Sum the order amounts for duplicate movies
                val cartIdList = films.map { it.cartId ?: 0 } // List of cart IDs for the same movie
                firstFilm.copy(
                    orderAmount = totalAmount,
                    cartIdList = cartIdList, // Combine the cart items into a single entry with the total order amount
                )
            }.orEmpty()

            Resource.Success(mergedFilms) // Return the merged list of cart items
        } catch (e: Exception) {
            Resource.Error(e.message.toString()) // Return error if an exception occurs
        }
    }
}
