package com.oktaygenc.cinechoice.ui.presentation.register.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.utils.Resource
import com.oktaygenc.cinechoice.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    companion object {
        private const val TAG = "RegisterViewModel"
    }

    private val _registerState = MutableStateFlow<Resource<String>>(Resource.Empty)
    val registerState: StateFlow<Resource<String>> = _registerState

    fun register(email: String, password: String) {
        Log.d(TAG, "Registration initiated")
        if (!isValidEmail(email)) {
            Log.w(TAG, "Invalid email format: ${email.take(3)}***")
            _registerState.value = Resource.Error("Geçersiz email formatı")
            return
        }
        if (!isValidPassword(password)) {
            Log.w(TAG, "Invalid password: too short")
            _registerState.value = Resource.Error("Şifre en az 6 karakter olmalıdır")
            return
        }

        _registerState.value = Resource.Loading
        viewModelScope.launch {
            Log.d(TAG, "Calling repository register")
            try {
                val result = authRepository.register(email, password)
                Log.d(TAG, "Registration result: $result")
                _registerState.value = result

                when (result) {
                    is Resource.Success -> {
                        Log.i(TAG, "Registration successful")
                    }
                    is Resource.Error -> {
                        Log.e(TAG, "Registration failed with error: ${result.message}")
                    }
                    else -> {
                        Log.d(TAG, "Registration resulted in: $result")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Registration failed in ViewModel", e)
                e.printStackTrace()
                _registerState.value = Resource.Error(e.message ?: "Beklenmeyen bir hata oluştu")
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches().also {
            Log.d(TAG, "Email validation result: $it for ${email.take(3)}***")
        }
    }

    private fun isValidPassword(password: String): Boolean {
        return (password.length >= 6).also {
            Log.d(TAG, "Password validation result: $it (length: ${password.length})")
        }
    }
}

