package com.oktaygenc.cinechoice.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.oktaygenc.cinechoice.data.datasource.AuthDataSource
import com.oktaygenc.cinechoice.utils.Resource
import com.oktaygenc.cinechoice.utils.UserSessionManager
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource
) {
    suspend fun login(email: String, password: String): Resource<String> {
        return try {
            val result = authDataSource.signInWithEmailPassword(email, password)
            try {
                val authResult = result.await()
                if (authResult.user != null) {
                    // Başarılı login sonrası email'i kaydet
                    UserSessionManager.setCurrentUser(email)
                    Log.i(TAG, "Login successful for user: ${authResult.user?.uid}")
                    Resource.Success("Login Successful")
                } else {
                    Log.w(TAG, "Login failed: User is null after successful authentication")
                    Resource.Error("Login Failed - User is null")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Login await failed", e)
                Resource.Error(e.message ?: "Login Failed")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Login process failed", e)
            Resource.Error(e.message ?: "Authentication failed")
        }
    }

    suspend fun register(email: String, password: String): Resource<String> {
        return try {
            val result = authDataSource.createUserWithEmailPassword(email, password)
            try {
                val authResult = result.await()
                if (authResult.user != null) {
                    // Başarılı kayıt sonrası email'i kaydet
                    UserSessionManager.setCurrentUser(email)
                    Log.i(TAG, "Registration successful for user: ${authResult.user?.uid}")
                    Resource.Success("Registration Successful")
                } else {
                    Log.w(TAG, "Registration failed: User is null after successful authentication")
                    Resource.Error("Registration Failed - User is null")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Registration await failed", e)
                Resource.Error(e.message ?: "Registration Failed")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Registration process failed", e)
            Resource.Error(e.message ?: "Registration failed")
        }
    }
}