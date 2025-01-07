package com.oktaygenc.cinechoice.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.oktaygenc.cinechoice.data.datasource.FavoriteDataSource
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val favoriteDataSource: FavoriteDataSource,
) {

    fun addFavoriteMovie(userId: String, movie: Movie): Task<Void> {
        return favoriteDataSource.addFavoriteMovie(userId, movie)
    }

    fun removeFavoriteMovie(userId: String, movieId: Int): Task<Void> {
        return favoriteDataSource.removeFavoriteMovie(userId, movieId)
    }

    fun getFavoriteMovies(userId: String): MutableLiveData<List<Movie>?> = favoriteDataSource.getFavoriteMovies(userId)
}


