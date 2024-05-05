package com.example.simple_movie_app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.simple_movie_app.data.database.converter.Converters
import com.example.simple_movie_app.data.database.dao.MovieDetailsDao
import com.example.simple_movie_app.data.database.dao.MoviesResponseDao
import com.example.simple_movie_app.data.database.entity.MovieDetails
import com.example.simple_movie_app.data.database.entity.MoviesResponse

@Database(
    entities = [MoviesResponse::class, MovieDetails::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun moviesResponseDao(): MoviesResponseDao

    abstract fun movieDetailsDao(): MovieDetailsDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration() // Strategy for migration if schemas are updated.
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private const val DB_NAME = "movie_cached_database"
    }
}