package com.example.simple_movie_app.data.database

import com.example.simple_movie_app.GlobalVariables
import com.example.simple_movie_app.data.database.entity.MovieDetails
import com.example.simple_movie_app.data.database.entity.MoviesResponse

internal object MovieDatabaseWrapper {

    suspend fun getMoviesOfPageNumber(pageNumber: Int, validThreshold: Long)
            : MoviesResponse? {
        val context = GlobalVariables.getApplicationContext()
            ?: throw IllegalStateException("Application context is not set.")
        return MovieDatabase.getDatabase(context).moviesResponseDao()
            .getMoviesResponseByPageNumber(pageNumber, validThreshold)
    }

    suspend fun insertMovies(movies: MoviesResponse) {
        val context = GlobalVariables.getApplicationContext()
            ?: throw IllegalStateException("Application context is not set.")
        return MovieDatabase.getDatabase(context).moviesResponseDao()
            .insertMoviesResponse(movies)
    }

    suspend fun deleteOldMoviesResponse(validThreshold: Long) {
        val context = GlobalVariables.getApplicationContext()
            ?: throw IllegalStateException("Application context is not set.")
        return MovieDatabase.getDatabase(context).moviesResponseDao()
            .deleteOldMoviesResponse(validThreshold)
    }

    suspend fun getMovieDetails(movieId: Int, validThreshold: Long): MovieDetails? {
        val context = GlobalVariables.getApplicationContext()
            ?: throw IllegalStateException("Application context is not set.")
        return MovieDatabase.getDatabase(context).movieDetailsDao()
            .getMovieDetails(movieId, validThreshold)
    }

    suspend fun insertMovieDetails(movie: MovieDetails) {
        val context = GlobalVariables.getApplicationContext()
            ?: throw IllegalStateException("Application context is not set.")
        return MovieDatabase.getDatabase(context).movieDetailsDao()
            .insertMovieDetails(movie)
    }

    suspend fun deleteOldMovieDetails(validThreshold: Long) {
        val context = GlobalVariables.getApplicationContext()
            ?: throw IllegalStateException("Application context is not set.")
        return MovieDatabase.getDatabase(context).movieDetailsDao()
            .deleteOldMovieDetails(validThreshold)
    }
}