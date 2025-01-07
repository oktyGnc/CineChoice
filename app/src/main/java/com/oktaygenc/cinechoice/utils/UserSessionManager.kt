package com.oktaygenc.cinechoice.utils

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

object UserSessionManager {
    private var currentUserEmail: String? = null
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun setCurrentUser(email: String) {
        // Email'den @ işaretinden önceki kısmı al
        currentUserEmail = email.substringBefore("@")
        Log.d("UserSessionManager", "Current user set to: $currentUserEmail")
    }

    private fun getUserName(): String {
        return firebaseAuth.currentUser?.email?.substringBefore("@") ?: "Unknown User"
    }

    fun getCurrentUser(): String {
        return currentUserEmail ?: getUserName()  // Burada getUserName() fonksiyonunu çağırıyoruz
    }

    fun clearCurrentUser() {
        currentUserEmail = null
        Log.d("UserSessionManager", "User session cleared")
    }
}
