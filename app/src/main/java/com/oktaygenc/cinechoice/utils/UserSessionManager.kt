package com.oktaygenc.cinechoice.utils

import android.util.Log

object UserSessionManager {
    private var currentUserEmail: String? = null

    fun setCurrentUser(email: String) {
        // Email'den @ işaretinden önceki kısmı al
        currentUserEmail = email.substringBefore("@")
        Log.d("UserSessionManager", "Current user set to: $currentUserEmail")
    }

    fun getCurrentUser(): String {
        return currentUserEmail ?: "oktaygenc"
    }

    fun clearCurrentUser() {
        currentUserEmail = null
        Log.d("UserSessionManager", "User session cleared")
    }
}