package com.oktaygenc.cinechoice.ui.presentation.explore.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import com.oktaygenc.cinechoice.usecase.GetAllMoviesUseCase
import com.oktaygenc.cinechoice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase, // UseCase injected to fetch movie data
) : ViewModel() {

    // State to hold the list of movies
    private val _movies = mutableStateOf<List<Movie>>(emptyList())
    val movies: State<List<Movie>> = _movies

    // State to hold the search query
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    init {
        loadMovies() // Initial loading of movies
    }

    // Function to load movies from the UseCase
    private fun loadMovies() {
        viewModelScope.launch {
            when (val movieList = getAllMoviesUseCase()) {
                is Resource.Success -> _movies.value =
                    movieList.data // On success, update the movie list
                is Resource.Error -> _movies.value =
                    emptyList() // On error, set movie list to empty
                Resource.Empty -> TODO() // Empty state - to be handled
                Resource.Loading -> TODO() // Loading state - to be handled
            }
        }
    }

    // Function to update search query
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query // Update the search query
    }

}


