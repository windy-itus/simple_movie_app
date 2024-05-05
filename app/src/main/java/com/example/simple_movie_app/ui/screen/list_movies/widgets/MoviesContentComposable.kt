package com.example.simple_movie_app.ui.screen.list_movies.widgets

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.simple_movie_app.data.database.entity.Movie
import com.example.simple_movie_app.ui.widgets.ErrorView
import com.example.simple_movie_app.ui.widgets.LoadingView

@Composable
fun MoviesContent(movies: LazyPagingItems<Movie>, navController: NavController) {
    if (movies.loadState.refresh is LoadState.Loading) {
        LoadingView()
    } else if (movies.itemCount == 0) {
        ErrorView("No movies found")
    } else {
        MoviesList(movies, navController)
    }
}