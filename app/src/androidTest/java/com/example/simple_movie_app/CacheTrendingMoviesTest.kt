package com.example.simple_movie_app

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.simple_movie_app.data.repository.MoviesRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CacheTrendingMoviesTest {
    private lateinit var repository: MoviesRepository
    private lateinit var mockWebServer: MockWebServer
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        mockWebServer = MockWebServer()
        mockWebServer.start()
        GlobalVariables.setServerEndpoint(mockWebServer.url("/").toString())
        GlobalVariables.setApplicationContext(context)
        GlobalVariables.setApiKey("fake_api_key")
        repository = MoviesRepository("fake_api_key")
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testCaching_TrendingMovies() = runBlocking {
        // Assume the initial state where no movies are cached
        // Setup MockWebServer to return a successful response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""{ "page": 1, "results": [{"id": 1, "title": "Test Movie", "release_date": "2023-11-03", "vote_average": 7.891}] }""")
        )

        // First call to fetch movies, should hit network and cache results
        val firstFetch = repository.getTrendingMovies(1)
        assert(firstFetch.isNotEmpty())

        // Modify the response to check if the cache is hit instead of network
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""{ "page": 1, "results": [{"id": 2, "title": "Another Movie", "release_date": "2024-05-02", "vote_average": 7.552}] }""")
        )

        // Set system time forward to still be within cache validity
        val secondFetch = repository.getTrendingMovies(1)

        // Assert that the cached data is returned instead of new network data
        assertTrue("Cached data expected but not found", secondFetch[0].id == 1)
    }
}