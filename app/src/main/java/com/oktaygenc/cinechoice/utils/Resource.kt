package com.oktaygenc.cinechoice.utils

// A sealed class that represents different states of data fetching or operation results.
sealed class Resource<out T> {
    // Represents a successful operation with data.
    data class Success<out T>(val data: T) : Resource<T>()

    // Represents an error with a message.
    data class Error(val message: String) : Resource<Nothing>()

    // Represents a loading state, used when an operation is in progress.
    data object Loading : Resource<Nothing>()

    // Represents an empty state, used when no data is available.
    data object Empty : Resource<Nothing>()
}

