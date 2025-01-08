package com.oktaygenc.cinechoice.data.datasource.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    companion object {
        private const val TAG = "AuthDataSource"
    }

    fun signInWithEmailPassword(email: String, password: String) =
        firebaseAuth.signInWithEmailAndPassword(email, password).also {
            Log.d(TAG, "Sign in request sent")
        }

    fun createUserWithEmailPassword(email: String, password: String) =
        firebaseAuth.createUserWithEmailAndPassword(email, password).also {
            Log.d(TAG, "Create user request sent")
        }

    fun getUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()
}