package com.example.simple_movie_app.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies_response")
data class MoviesResponse(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @ColumnInfo(name = "cached_at") // Column to store the timestamp of caching
    val cachedAt: Long = System.currentTimeMillis()
)