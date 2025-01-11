package com.oktaygenc.cinechoice.data.datasource.favorite

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import javax.inject.Inject

class FavoriteDataSource @Inject constructor(
    firestore: FirebaseFirestore,
) {
    // Reference to the "users" collection in Firestore
    private val usersCollection = firestore.collection("users")

    // Adds a movie to the user's list of favorite movies
    fun addFavoriteMovie(userId: String, movie: Movie): Task<Void> {
        return usersCollection.document(userId).collection("favorites")
            .document(movie.id.toString()).set(movie)
    }

    // Removes a movie from the user's list of favorite movies
    fun removeFavoriteMovie(userId: String, movieId: Int): Task<Void> {
        return usersCollection.document(userId).collection("favorites").document(movieId.toString())
            .delete()
    }

    // Retrieves the user's list of favorite movies
    fun getFavoriteMovies(userId: String): Task<QuerySnapshot> {
        return usersCollection.document(userId).collection("favorites").get()
    }

    // Checks if a specific movie is in the user's list of favorite movies
    fun checkFavoriteMovie(userId: String, movieId: Int): Task<QuerySnapshot> {
        return usersCollection.document(userId).collection("favorites").whereEqualTo("id", movieId)
            .get()
    }
}