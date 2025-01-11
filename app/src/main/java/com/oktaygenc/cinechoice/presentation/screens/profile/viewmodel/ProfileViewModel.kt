package com.oktaygenc.cinechoice.presentation.screens.profile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.oktaygenc.cinechoice.utils.Resource
import com.oktaygenc.cinechoice.utils.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {
    // State flow to manage user authentication state
    private val _userState = MutableStateFlow<Resource<FirebaseUser>>(Resource.Empty)
    val userState: StateFlow<Resource<FirebaseUser>> = _userState

    // Initialize view model by fetching current user
    init {
        getCurrentUser()
    }

    // Retrieve and update current user state
    private fun getCurrentUser() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            _userState.value = Resource.Success(currentUser)
        } else {
            _userState.value = Resource.Error("User not found")
        }
    }

    // Handle user logout process
    fun logout(onLogoutSuccess: () -> Unit) {
        try {
            firebaseAuth.signOut()
            UserSessionManager.clearCurrentUser()
            onLogoutSuccess()
        } catch (e: Exception) {
            Log.e("ProfileViewModel", "Logout failed", e)
        }
    }

    // Extract username from email
    fun getUserName(): String {
        return firebaseAuth.currentUser?.email?.substringBefore("@") ?: "Unknown User"
    }

    // Retrieve user email
    fun getUserEmail(): String {
        return firebaseAuth.currentUser?.email ?: "No email"
    }
}
