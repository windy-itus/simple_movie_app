package com.example.simple_movie_app.data.repository

import com.example.simple_movie_app.data.database.MovieDatabaseWrapper
import com.example.simple_movie_app.data.database.entity.Movie
import com.example.simple_movie_app.data.database.entity.MovieDetails
import com.example.simple_movie_app.data.network.ApiServiceManager

class MoviesRepository(private val apiKey: String) {
    private val cacheValidity = 24 * 60 * 60 * 1000 // 24 hours in milliseconds

    suspend fun getTrendingMovies(pageNumber: Int): List<Movie> {
        val currentTime = System.currentTimeMillis()
        val validThreshold = currentTime - cacheValidity
        val cachedMovies =
            MovieDatabaseWrapper.getMoviesOfPageNumber(pageNumber, validThreshold)
        if (cachedMovies != null) {
            return cachedMovies.results
        } else {
            // Have outdated cached or fetch new page
            MovieDatabaseWrapper.deleteOldMoviesResponse(validThreshold)

            // Fetch data from cloud
            ApiServiceManager.tMDBApiService?.let { apiService ->
                val response = apiService.getTrendingMovies(apiKey, pageNumber)
                if (response.isSuccessful) {
                    response.body()?.let {
                        MovieDatabaseWrapper.insertMovies(it)
                        return it.results
                    }
                }
            }
        }
        return emptyList()  // Return an empty list if there are no results or an error occurs
    }

    suspend fun searchMovies(query: String, pageNumber: Int): List<Movie> {
        if (query.isNotEmpty()) {
            ApiServiceManager.tMDBApiService?.let { apiService ->
                // TODO: setup apiKey at first time
                val response = apiService.searchMovies(apiKey, query, pageNumber)
                if (response.isSuccessful) {
                    return response.body()?.results ?: emptyList()
                }
            }
        }
        // Return an empty list if the search query is empty or got any error
        return emptyList()
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetails? {
        val currentTime = System.currentTimeMillis()
        val validThreshold = currentTime - cacheValidity
        val cachedMovieDetails =
            MovieDatabaseWrapper.getMovieDetails(movieId, validThreshold)
        if (cachedMovieDetails != null) {
            return cachedMovieDetails
        } else {
            // Have outdated cached or fetch new page
            MovieDatabaseWrapper.deleteOldMovieDetails(validThreshold)

            // Fetch data from cloud
            ApiServiceManager.tMDBApiService?.let { apiService ->
                val response = apiService.getMovieDetails(movieId, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let {
                        MovieDatabaseWrapper.insertMovieDetails(it)
                        return it
                    }
                }
            }
        }
        return null
    }
}
