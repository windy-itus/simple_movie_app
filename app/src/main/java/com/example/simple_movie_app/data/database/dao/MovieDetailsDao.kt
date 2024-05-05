package com.example.simple_movie_app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simple_movie_app.data.database.entity.MovieDetails

@Dao
interface MovieDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(details: MovieDetails)

    @Query("SELECT * FROM movie_details WHERE id = :movieId AND cached_at >= :threshold")
    suspend fun getMovieDetails(movieId: Int, threshold: Long): MovieDetails?

    @Query("DELETE FROM movie_details WHERE cached_at < :threshold")
    suspend fun deleteOldMovieDetails(threshold: Long)
}