package com.example.simple_movie_app.ui.screen.list_movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.simple_movie_app.ui.screen.list_movies.widgets.HeaderText
import com.example.simple_movie_app.ui.screen.list_movies.widgets.MoviesContent
import com.example.simple_movie_app.ui.screen.list_movies.widgets.SearchBar
import com.example.simple_movie_app.view_model.MoviesViewModel


@Composable
fun MoviesScreen(viewModel: MoviesViewModel, navController: NavController) {
    val query by viewModel.query.collectAsState()
    val isSearching by viewModel.isSearching.observeAsState(false)
    val headerText = if (isSearching) "Search results for: $query" else "Trending movies"

    val movies = viewModel.movies.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(query, viewModel::updateSearchQuery)
        HeaderText(headerText)
        MoviesContent(movies, navController)
    }
}


