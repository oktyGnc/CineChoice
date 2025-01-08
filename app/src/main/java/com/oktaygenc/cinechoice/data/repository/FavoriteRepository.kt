package com.oktaygenc.cinechoice.data.repository

import com.oktaygenc.cinechoice.data.datasource.FavoriteDataSource
import com.oktaygenc.cinechoice.data.datasource.auth.AuthDataSource
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import com.oktaygenc.cinechoice.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val favoriteDataSource: FavoriteDataSource,
    private val authDataSource: AuthDataSource,
) {
    suspend fun addFavoriteMovie(movie: Movie): Resource<String> {
        return try {
            val userId = authDataSource.getUserId()
            favoriteDataSource.addFavoriteMovie(userId, movie).await()
            Resource.Success("Movie added successfully")
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun removeFavoriteMovie(userId: String, movieId: Int): Resource<String> {
        return try {
            favoriteDataSource.removeFavoriteMovie(userId, movieId).await()
            Resource.Success("Movie removed successfully")
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun getFavoriteMovies(userId: String): Resource<List<Movie>> {
        return try {
            val snapshot = favoriteDataSource.getFavoriteMovies(userId).await()

            val movies = snapshot.documents.mapNotNull { document ->
                document.toObject(Movie::class.java)
            }

            Resource.Success(movies)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun checkFavoriteMovie(userId: String, movieId: Int): Resource<Boolean> {
        return try {
            val snapshot = favoriteDataSource.checkFavoriteMovie(userId, movieId).await()
            Resource.Success(snapshot.documents.isNotEmpty())
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}


