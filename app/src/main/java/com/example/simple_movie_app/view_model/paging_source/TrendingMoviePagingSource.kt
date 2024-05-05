package com.example.simple_movie_app.view_model.paging_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simple_movie_app.data.database.entity.Movie
import com.example.simple_movie_app.data.repository.MoviesRepository

class TrendingMoviePagingSource(private val repository: MoviesRepository) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageNumber = params.key ?: 1 // Start from page 1 if null
        return try {
            Log.d("MoviePagingSource", "load page=$pageNumber")
            val movies = repository.getTrendingMovies(pageNumber)
            val nextKey = if (movies.isEmpty()) null else pageNumber + 1
            LoadResult.Page(
                data = movies,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}