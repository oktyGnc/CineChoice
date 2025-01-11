package com.oktaygenc.cinechoice.data.repository.auth

import android.content.ContentValues.TAG
import android.util.Log
import com.oktaygenc.cinechoice.data.datasource.auth.AuthDataSource
import com.oktaygenc.cinechoice.utils.Resource
import com.oktaygenc.cinechoice.utils.UserSessionManager
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource // Injecting AuthDataSource to handle authentication
) {
    // Function to handle user login
    suspend fun login(email: String, password: String): Resource<String> {
        return try {
            // Attempt to sign in with the provided credentials
            val result = authDataSource.signInWithEmailPassword(email, password)
            try {
                // Await the result of the sign-in attempt
                val authResult = result.await()
                if (authResult.user != null) {
                    // If login is successful, set the current user session
                    UserSessionManager.setCurrentUser(email)
                    Log.i(TAG, "Login successful for user: ${authResult.user?.uid}")
                    Resource.Success("Login Successful") // Return success result
                } else {
                    // If the user is null after authentication, log the failure
                    Log.w(TAG, "Login failed: User is null after successful authentication")
                    Resource.Error("Login Failed - User is null") // Return error result
                }
            } catch (e: Exception) {
                // Handle any exceptions that occur during the await process
                Log.e(TAG, "Login await failed", e)
                Resource.Error(e.message ?: "Login Failed") // Return error result with exception message
            }
        } catch (e: Exception) {
            // Handle any exceptions that occur during the sign-in process
            Log.e(TAG, "Login process failed", e)
            Resource.Error(e.message ?: "Authentication failed") // Return error result with exception message
        }
    }

    // Function to handle user registration
    suspend fun register(email: String, password: String): Resource<String> {
        return try {
            // Attempt to create a new user with the provided credentials
            val result = authDataSource.createUserWithEmailPassword(email, password)
            try {
                // Await the result of the registration attempt
                val authResult = result.await()
                if (authResult.user != null) {
                    // If registration is successful, set the current user session
                    UserSessionManager.setCurrentUser(email)
                    Log.i(TAG, "Registration successful for user: ${authResult.user?.uid}")
                    Resource.Success("Registration Successful") // Return success result
                } else {
                    // If the user is null after registration, log the failure
                    Log.w(TAG, "Registration failed: User is null after successful authentication")
                    Resource.Error("Registration Failed - User is null") // Return error result
                }
            } catch (e: Exception) {
                // Handle any exceptions that occur during the await process
                Log.e(TAG, "Registration await failed", e)
                Resource.Error(e.message ?: "Registration Failed") // Return error result with exception message
            }
        } catch (e: Exception) {
            // Handle any exceptions that occur during the registration process
            Log.e(TAG, "Registration process failed", e)
            Resource.Error(e.message ?: "Registration failed") // Return error result with exception message
        }
    }
}
