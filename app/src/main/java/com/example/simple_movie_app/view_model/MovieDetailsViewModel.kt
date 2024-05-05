package com.example.simple_movie_app.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simple_movie_app.data.database.entity.MovieDetails
import com.example.simple_movie_app.data.repository.MoviesRepository
import com.example.simple_movie_app.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MoviesRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<MovieDetails?>>(UiState.Loading)
    val uiState: StateFlow<UiState<MovieDetails?>> = _uiState.asStateFlow()

    fun fetchMovieDetails(movieId: Int) {
        _uiState.value = UiState.Loading  // Set loading state
        viewModelScope.launch {
            try {
                val details = repository.getMovieDetails(movieId)
                _uiState.value = UiState.Success(details)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load movie details")
            }
        }
    }
}