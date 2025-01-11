package com.oktaygenc.cinechoice.data.datasource.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    companion object {
        private const val TAG = "AuthDataSource" // Tag used for logging
    }

    // Method to sign in a user using email and password
    fun signInWithEmailPassword(email: String, password: String) =
        firebaseAuth.signInWithEmailAndPassword(email, password).also {
            Log.d(TAG, "Sign in request sent") // Log the sign-in attempt
        }

    // Method to create a new user with email and password
    fun createUserWithEmailPassword(email: String, password: String) =
        firebaseAuth.createUserWithEmailAndPassword(email, password).also {
            Log.d(TAG, "Create user request sent") // Log the user creation attempt
        }

    // Method to get the current user's ID
    fun getUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()
}

