package com.oktaygenc.cinechoice.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import javax.inject.Inject

class FavoriteDataSource @Inject constructor(val usersCollection: CollectionReference) {
    private val _favorites = MutableLiveData<List<Movie>?>()
    val favoriteMovies: LiveData<List<Movie>?> = _favorites

    fun addFavoriteMovie(userId: String, movie: Movie): Task<Void> {
        return usersCollection.document(userId)
            .collection("favorites")
            .document(movie.id.toString())
            .set(movie)
            .addOnSuccessListener {
                Log.d("Firestore", "Movie added successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error adding movie: $exception")
            }
    }

    fun removeFavoriteMovie(userId: String, movieId: Int): Task<Void> {
        Log.d("Firestore", "Removing movieId: $movieId for userId: $userId")
        return usersCollection.document(userId)
            .collection("favorites")
            .document(movieId.toString())
            .delete().addOnFailureListener { exception ->
                Log.e("Firestore", "Error removing movie: $exception")

            }
    }

    fun getFavoriteMovies(userId: String): MutableLiveData<List<Movie>?> {
        usersCollection.document(userId)
            .collection("favorites")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    _favorites.value = emptyList()
                    return@addSnapshotListener
                }
                val movies = snapshot?.documents?.mapNotNull { it.toObject(Movie::class.java) }
                _favorites.value = movies
                Log.d("favorites", "get fav ds $movies")
            }
        return _favorites
    }
}