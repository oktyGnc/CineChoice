package com.oktaygenc.cinechoice.ui.presentation.movielist.state

import com.oktaygenc.cinechoice.data.model.entitiy.Movie


data class MoviesState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null, // İşlem başarı mesajları
    val selectedCategory: String = "All", // Seçilen kategori
    val filteredMovies: List<Movie> = emptyList(), // Kategoriye göre filtrelenmiş filmler
)
