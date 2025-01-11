package com.oktaygenc.cinechoice.utils

// Constants object holding the base URLs and utility functions for the app.
object Constants {
    // The base URL for the API.
    const val BASE_URL = "http://kasimadalan.pe.hu/"

    // Private constant holding the base URL for images.
    private const val IMAGE_BASE_URL = "http://kasimadalan.pe.hu/movies/images/"

    // Utility function to get the full image URL given an image name.
    fun getImageUrl(imageName: String): String {
        return "${IMAGE_BASE_URL}${imageName}"
    }
}
