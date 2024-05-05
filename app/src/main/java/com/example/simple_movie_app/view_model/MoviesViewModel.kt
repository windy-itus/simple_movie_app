package com.example.simple_movie_app.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.simple_movie_app.data.database.entity.Movie
import com.example.simple_movie_app.data.repository.MoviesRepository
import com.example.simple_movie_app.view_model.paging_source.FoundMoviePagingSource
import com.example.simple_movie_app.view_model.paging_source.TrendingMoviePagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

open class MoviesViewModel(private val repository: MoviesRepository) : ViewModel() {
    private val searchQuery = MutableStateFlow("")

    open val query = searchQuery.asStateFlow()
    open val isSearching: LiveData<Boolean> = searchQuery.map { it.isNotEmpty() }.asLiveData()

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    open val movies: Flow<PagingData<Movie>> = searchQuery.flatMapLatest { query ->
        if (query.isEmpty()) {
            Pager(
                PagingConfig(pageSize = 20, enablePlaceholders = false),
                pagingSourceFactory = { TrendingMoviePagingSource(repository) }
            ).flow
        } else {
            Pager(
                PagingConfig(pageSize = 20, enablePlaceholders = false),
                pagingSourceFactory = { FoundMoviePagingSource(repository, query) }
            ).flow
        }
    }.cachedIn(viewModelScope)
}