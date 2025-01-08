package com.oktaygenc.cinechoice.data.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import javax.inject.Inject

class FavoriteDataSource @Inject constructor(
    firestore: FirebaseFirestore,
) {
    private val usersCollection = firestore.collection("users")

    fun addFavoriteMovie(userId: String, movie: Movie): Task<Void> {
        return usersCollection.document(userId).collection("favorites")
            .document(movie.id.toString()).set(movie)
    }

    fun removeFavoriteMovie(userId: String, movieId: Int): Task<Void> {
        return usersCollection.document(userId).collection("favorites").document(movieId.toString())
            .delete()
    }

    fun getFavoriteMovies(userId: String): Task<QuerySnapshot> {
        return usersCollection.document(userId).collection("favorites").get()
    }

    fun checkFavoriteMovie(userId: String, movieId: Int): Task<QuerySnapshot> {
        return usersCollection.document(userId).collection("favorites").whereEqualTo("id", movieId)
            .get()
    }
}