package com.example.simple_movie_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simple_movie_app.data.repository.MoviesRepository
import com.example.simple_movie_app.ui.screen.list_movies.MoviesScreen
import com.example.simple_movie_app.ui.screen.movie_details.MovieDetailsScreen
import com.example.simple_movie_app.ui.theme.Simple_movie_appTheme
import com.example.simple_movie_app.view_model.MovieDetailsViewModel
import com.example.simple_movie_app.view_model.MoviesViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val moviesRepository = MoviesRepository(
            apiKey = GlobalVariables.getApiKey() ?: ""
        )
        setContent {
            Simple_movie_appTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        MoviesViewModel(moviesRepository), MovieDetailsViewModel(moviesRepository)
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(moviesViewModel: MoviesViewModel, movieDetailsViewModel: MovieDetailsViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "moviesList") {
        composable("moviesList") {
            MoviesScreen(viewModel = moviesViewModel, navController = navController)
        }
        composable("movieDetails/{movieId}") { backStackEntry ->
            // Extract movieId from backStackEntry
            val movieId =
                backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: return@composable
            MovieDetailsScreen(viewModel = movieDetailsViewModel, movieId = movieId)
        }
    }
}
