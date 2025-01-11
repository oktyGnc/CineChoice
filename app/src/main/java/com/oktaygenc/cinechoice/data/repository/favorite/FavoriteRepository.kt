package com.oktaygenc.cinechoice.data.repository.favorite

import com.oktaygenc.cinechoice.data.datasource.favorite.FavoriteDataSource
import com.oktaygenc.cinechoice.data.datasource.auth.AuthDataSource
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import com.oktaygenc.cinechoice.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val favoriteDataSource: FavoriteDataSource,
    private val authDataSource: AuthDataSource,
) {
    // Function to add a movie to the user's favorites
    suspend fun addFavoriteMovie(movie: Movie): Resource<String> {
        return try {
            // Get the current user's ID
            val userId = authDataSource.getUserId()
            // Add the movie to the favorites collection for the user
            favoriteDataSource.addFavoriteMovie(userId, movie).await()
            Resource.Success("Movie added successfully") // Return success result
        } catch (e: Exception) {
            Resource.Error(e.message.toString()) // Return error result if an exception occurs
        }
    }

    // Function to remove a movie from the user's favorites
    suspend fun removeFavoriteMovie(userId: String, movieId: Int): Resource<String> {
        return try {
            // Remove the movie from the favorites collection for the user
            favoriteDataSource.removeFavoriteMovie(userId, movieId).await()
            Resource.Success("Movie removed successfully") // Return success result
        } catch (e: Exception) {
            Resource.Error(e.message.toString()) // Return error result if an exception occurs
        }
    }

    // Function to get all favorite movies of a user
    suspend fun getFavoriteMovies(userId: String): Resource<List<Movie>> {
        return try {
            // Fetch the list of favorite movies from the database
            val snapshot = favoriteDataSource.getFavoriteMovies(userId).await()
            // Convert the query snapshot into a list of Movie objects
            val movies = snapshot.documents.mapNotNull { document ->
                document.toObject(Movie::class.java)
            }
            Resource.Success(movies) // Return the list of movies as success result
        } catch (e: Exception) {
            Resource.Error(e.message.toString()) // Return error result if an exception occurs
        }
    }

    // Function to check if a movie is already in the user's favorites
    suspend fun checkFavoriteMovie(userId: String, movieId: Int): Resource<Boolean> {
        return try {
            // Check if the movie is in the favorites collection for the user
            val snapshot = favoriteDataSource.checkFavoriteMovie(userId, movieId).await()
            Resource.Success(snapshot.documents.isNotEmpty()) // Return true if the movie is found
        } catch (e: Exception) {
            Resource.Error(e.message.toString()) // Return error result if an exception occurs
        }
    }
}



