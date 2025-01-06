package com.oktaygenc.cinechoice.ui.presentation.login.viewmodel

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
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    companion object {
        private const val TAG = "LoginViewModel"
    }

    private val _loginState = MutableStateFlow<Resource<String>>(Resource.Empty)
    val loginState: StateFlow<Resource<String>> = _loginState

    fun login(email: String, password: String) {
        Log.d(TAG, "Login initiated")
        _loginState.value = Resource.Loading
        viewModelScope.launch {
            Log.d(TAG, "Calling repository login")
            try {
                val result = authRepository.login(email, password)
                Log.d(TAG, "Login result: $result")
                _loginState.value = result
            } catch (e: Exception) {
                Log.e(TAG, "Login failed in ViewModel", e)
                _loginState.value = Resource.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }
}
