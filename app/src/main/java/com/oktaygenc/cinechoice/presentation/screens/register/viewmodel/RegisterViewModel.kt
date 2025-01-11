package com.oktaygenc.cinechoice.presentation.screens.register.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.data.repository.auth.AuthRepository
import com.oktaygenc.cinechoice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    // Companion object for logging tag
    companion object {
        private const val TAG = "RegisterViewModel"
    }

    // State flow to manage registration process
    private val _registerState = MutableStateFlow<Resource<String>>(Resource.Empty)
    val registerState: StateFlow<Resource<String>> = _registerState

    // Main registration method with validation
    fun register(email: String, password: String) {
        // Validate email format
        if (!isValidEmail(email)) {
            _registerState.value = Resource.Error("Invalid email format")
            return
        }

        // Validate password length
        if (!isValidPassword(password)) {
            _registerState.value = Resource.Error("Password must be at least 6 characters")
            return
        }

        // Initiate registration process
        _registerState.value = Resource.Loading
        viewModelScope.launch {
            try {
                // Call repository to register user
                val result = authRepository.register(email, password)
                _registerState.value = result

                // Handle different registration outcomes
                when (result) {
                    is Resource.Success -> Log.i(TAG, "Registration successful")
                    is Resource.Error -> Log.e(TAG, "Registration failed")
                    else -> Log.d(TAG, "Registration process completed")
                }
            } catch (e: Exception) {
                // Handle unexpected errors
                _registerState.value = Resource.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

    // Email validation method
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Password validation method
    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
}

