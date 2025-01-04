package com.oktaygenc.cinechoice.ui.presentation.explore.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.data.model.Movie
import com.oktaygenc.cinechoice.usecase.GetAllMoviesUseCase
import com.oktaygenc.cinechoice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
) : ViewModel() {

    // Movie verisini tutacak bir state
    private val _movies = mutableStateOf<List<Movie>>(emptyList())
    val movies: State<List<Movie>> = _movies

    // Search query'yi tutacak bir state
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            when (val movieList = getAllMoviesUseCase()) {
                is Resource.Success -> _movies.value = movieList.data
                is Resource.Error -> _movies.value = emptyList()
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

}

