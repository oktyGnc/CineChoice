package com.oktaygenc.cinechoice.utils

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

// A singleton object responsible for managing user session information.
object UserSessionManager {
    // Variable to store the current user's email, which is used to identify the user session.
    private var currentUserEmail: String? = null

    // FirebaseAuth instance for authentication-related operations.
    private val firebaseAuth = FirebaseAuth.getInstance()

    // Sets the current user by extracting the email prefix (before "@") and saving it.
    fun setCurrentUser(email: String) {
        currentUserEmail = email.substringBefore("@")
        Log.d("UserSessionManager", "Current user set to: $currentUserEmail")
    }

    // Gets the username (email prefix) of the currently authenticated user from Firebase.
    private fun getUserName(): String {
        return firebaseAuth.currentUser?.email?.substringBefore("@") ?: "Unknown User"
    }

    // Retrieves the current user's email (or username) if available, otherwise returns the username from FirebaseAuth.
    fun getCurrentUser(): String {
        return currentUserEmail ?: getUserName()
    }

    // Clears the current user session by resetting the currentUserEmail.
    fun clearCurrentUser() {
        currentUserEmail = null
        Log.d("UserSessionManager", "User session cleared")
    }
}

