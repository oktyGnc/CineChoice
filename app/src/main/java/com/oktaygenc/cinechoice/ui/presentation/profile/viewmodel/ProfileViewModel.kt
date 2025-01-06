package com.oktaygenc.cinechoice.ui.presentation.profile.viewmodel

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

// ProfileViewModel.kt
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _userState = MutableStateFlow<Resource<FirebaseUser>>(Resource.Empty)
    val userState: StateFlow<Resource<FirebaseUser>> = _userState

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            _userState.value = Resource.Success(currentUser)
        } else {
            _userState.value = Resource.Error("User not found")
        }
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        try {
            firebaseAuth.signOut()
            UserSessionManager.clearCurrentUser()
            onLogoutSuccess()
        } catch (e: Exception) {
            Log.e("ProfileViewModel", "Logout failed", e)
        }
    }

    fun getUserName(): String {
        return firebaseAuth.currentUser?.email?.substringBefore("@") ?: "Unknown User"
    }

    fun getUserEmail(): String {
        return firebaseAuth.currentUser?.email ?: "No email"
    }
}
