package com.example.simple_movie_app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simple_movie_app.data.database.entity.MoviesResponse

@Dao
interface MoviesResponseDao {
    @Query("SELECT * FROM movies_response WHERE page = :pageNumber AND cached_at >= :threshold")
    suspend fun getMoviesResponseByPageNumber(pageNumber: Int, threshold: Long): MoviesResponse?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesResponse(movies: MoviesResponse)

    @Query("DELETE FROM movies_response WHERE cached_at < :threshold")
    suspend fun deleteOldMoviesResponse(threshold: Long)
}