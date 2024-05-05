import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.simple_movie_app.MainActivity
import com.example.simple_movie_app.data.database.entity.Genre
import com.example.simple_movie_app.data.database.entity.Movie
import com.example.simple_movie_app.data.database.entity.MovieDetails
import com.example.simple_movie_app.ui.UiState
import com.example.simple_movie_app.view_model.MovieDetailsViewModel
import com.example.simple_movie_app.view_model.MoviesViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`


@RunWith(AndroidJUnit4::class)
class MoviesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>() // Use your host activity

    @Test
    fun testHeaderTextDisplaysTrendingMoviesCorrectly() {
        composeTestRule.onNodeWithText("Search").performTextInput("")

        // Assert that the HeaderText is correctly set
        composeTestRule.onNodeWithText("Trending movies").assertIsDisplayed()
    }

    @Test
    fun testHeaderTextDisplaysSearchResultsCorrectly() {
        composeTestRule.onNodeWithText("Search").performTextInput("Godzilla")

        // Assert that the HeaderText is correctly set
        composeTestRule.onNodeWithText("Search results for: Godzilla").assertIsDisplayed()
    }

    @Test
    fun testOpenMovieDetailsScreenWhenClickOnMovieItem() {
        // Mock ViewModel
        val mockMoviesViewModel = Mockito.mock(MoviesViewModel::class.java)
        val mockMovieDetailsViewModel = Mockito.mock(MovieDetailsViewModel::class.java)

        `when`(mockMoviesViewModel.movies).thenReturn(Pager(PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
            pagingSourceFactory = { createMockPagingSource() }).flow
        )

        // fetch movie with random id
        val mockUiState =
            MutableStateFlow<UiState<MovieDetails?>>(UiState.Success(createMockMovieDetails()))
        `when`(mockMovieDetailsViewModel.uiState).thenReturn(mockUiState)


        // TODO: Handle open new screen after click on a movie item
        assert(true)
    }

    private fun createMockPagingSource(): PagingSource<Int, Movie> {
        return object : PagingSource<Int, Movie>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
                // Creating a list of movies to populate the PagingData
                val movies = listOf(
                    Movie(
                        id = 1,
                        title = "Godzilla",
                        posterPath = "",
                        releaseDate = "1998",
                        voteAverage = 5.0
                    ), Movie(
                        id = 2,
                        title = "Dune: Part Two",
                        posterPath = "",
                        releaseDate = "2000",
                        voteAverage = 6.0
                    ), Movie(
                        id = 3,
                        title = "Chief of Station",
                        posterPath = "",
                        releaseDate = "2002",
                        voteAverage = 7.0
                    )
                )
                return LoadResult.Page(
                    data = movies, prevKey = null, // Only one page in this example
                    nextKey = null
                )
            }

            override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
                return state.anchorPosition
            }
        }
    }

    private fun createMockMovieDetails(): MovieDetails {
        return MovieDetails(
            id = 1,
            title = "Godzilla",
            overview = "A giant radioactive monster called Godzilla appears to wreak destruction on mankind.",
            posterPath = "https://image.tmdb.org/t/p/w500/xyz.jpg",
            releaseDate = "2014-05-16",
            genres = listOf(
                Genre(id = 1, name = "Action"),
                Genre(id = 1, name = "Science Fiction"),
                Genre(id = 1, name = "Drama")
            ),
            voteAverage = 6.6,
            voteCount = 100,
            video = false,
            status = "Completed",
            spokenLanguages = emptyList(),
            revenue = 1000,
            productionCountries = emptyList(),
            productionCompanies = emptyList(),
            popularity = 7.9,
            originalTitle = "",
            originalLanguage = "",
            budget = 1000,
            adult = false,
            backdropPath = null,
            homepage = null,
            belongsToCollection = null,
            imdbId = null,
            runtime = null,
            tagline = null,
        )
    }
}