package com.oktaygenc.cinechoice.utils

object Constants {
    const val BASE_URL = "http://kasimadalan.pe.hu/"
    private const val IMAGE_BASE_URL = "http://kasimadalan.pe.hu/movies/images/"

    fun getImageUrl(imageName: String): String {
        return "${IMAGE_BASE_URL}${imageName}"
    }
}