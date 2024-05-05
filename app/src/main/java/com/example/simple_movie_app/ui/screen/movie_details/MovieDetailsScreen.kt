package com.example.simple_movie_app.ui.screen.movie_details

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simple_movie_app.data.database.entity.MovieDetails
import com.example.simple_movie_app.ui.UiState
import com.example.simple_movie_app.ui.screen.movie_details.widgets.Chip
import com.example.simple_movie_app.ui.screen.movie_details.widgets.ImageComposable
import com.example.simple_movie_app.ui.screen.movie_details.widgets.LinkText
import com.example.simple_movie_app.ui.widgets.ErrorView
import com.example.simple_movie_app.ui.widgets.LoadingView
import com.example.simple_movie_app.view_model.MovieDetailsViewModel

@Composable
fun MovieDetailsScreen(viewModel: MovieDetailsViewModel, movieId: Int) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetails(movieId)
    }

    when (uiState) {
        is UiState.Loading -> LoadingView()
        is UiState.Success -> MovieDetailsContent((uiState as UiState.Success<MovieDetails?>).data)
        is UiState.Error -> ErrorView((uiState as UiState.Error).message)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsContent(movieDetails: MovieDetails?) {
    if (movieDetails != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Movie Details") },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                movieDetails.posterPath?.let {
                    ImageComposable(
                        imageUrl = "https://image.tmdb.org/t/p/w500$it",
                        contentDescription = "Movie Poster",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)  // Adjusted for better aspect ratio
                    )
                }
                Text(
                    text = movieDetails.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Release Date: ${movieDetails.releaseDate}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Runtime: ${movieDetails.runtime} minutes",
                    style = MaterialTheme.typography.bodyLarge
                )
                movieDetails.tagline?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyMedium)
                }
                Text(
                    text = "Rating: ${movieDetails.voteAverage} (${movieDetails.voteCount} votes)",
                    style = MaterialTheme.typography.bodyLarge
                )
                // Displaying genres using a row for horizontal scrolling
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 10.dp)  // Add padding around the Row for better UI experience
                ) {
                    movieDetails.genres.forEach { genre ->
                        Chip(label = { Text(genre.name) }) // Chips now have end padding
                    }
                }
                Text(
                    text = "Overview: ${movieDetails.overview}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
                movieDetails.homepage?.let { url ->
                    LinkText(url)
                }
            }
        }
    } else {
        // Handle null case possibly with a placeholder or error message
        Text("No details available")
    }
}