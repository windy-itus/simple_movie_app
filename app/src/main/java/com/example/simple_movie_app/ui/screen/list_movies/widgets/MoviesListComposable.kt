package com.example.simple_movie_app.ui.screen.list_movies.widgets

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.example.simple_movie_app.data.database.entity.Movie

@Composable
fun MoviesList(movies: LazyPagingItems<Movie>, navController: NavController) {
    LazyColumn {
        items(movies.itemCount) { index ->
            movies[index]?.let { movie ->
                MovieItem(movie = movie, navController = navController)
            }
        }
    }
}