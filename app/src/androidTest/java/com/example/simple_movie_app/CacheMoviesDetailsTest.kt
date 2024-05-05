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
class CacheMoviesDetailsTest {
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
    fun testCaching_MoviesDetails() = runBlocking {
        // Assume the initial state where no movies are cached
        // Setup MockWebServer to return a successful response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(
                    """{
                        "adult": false,
                        "backdrop_path": "/bWIIWhnaoWx3FTVXv6GkYDv3djL.jpg",
                        "belongs_to_collection": null,
                        "budget": 15000000,
                        "genres": [
                            {
                                "id": 878,
                                "name": "Science Fiction"
                            },
                            {
                                "id": 27,
                                "name": "Horror"
                            },
                            {
                                "id": 28,
                                "name": "Action"
                            }
                        ],
                        "homepage": "https://tickets.godzilla.com",
                        "id": 940721,
                        "imdb_id": "tt23289160",
                        "origin_country": [
                            "JP"
                        ],
                        "original_language": "ja",
                        "original_title": "ゴジラ-1.0",
                        "overview": "Postwar Japan is at its lowest point when a new crisis emerges in the form of a giant monster, baptized in the horrific power of the atomic bomb.",
                        "popularity": 1017.628,
                        "poster_path": "/hkxxMIGaiCTmrEArK7J56JTKUlB.jpg",
                        "production_companies": [
                            {
                                "id": 882,
                                "logo_path": "/iDw9Xxok1d9WAM2zFicI8p3khTH.png",
                                "name": "TOHO",
                                "origin_country": "JP"
                            },
                            {
                                "id": 182161,
                                "logo_path": "/wvG4lK0m76M6jK8WbWkXNecA7SP.png",
                                "name": "TOHO Studios",
                                "origin_country": "JP"
                            },
                            {
                                "id": 12386,
                                "logo_path": "/oxvw2Mrq3GcTxFc2mlT7E5tpek7.png",
                                "name": "Robot Communications",
                                "origin_country": "JP"
                            }
                        ],
                        "production_countries": [
                            {
                                "iso_3166_1": "JP",
                                "name": "Japan"
                            }
                        ],
                        "release_date": "2023-11-03",
                        "revenue": 115857413,
                        "runtime": 125,
                        "spoken_languages": [
                            {
                                "english_name": "English",
                                "iso_639_1": "en",
                                "name": "English"
                            },
                            {
                                "english_name": "Japanese",
                                "iso_639_1": "ja",
                                "name": "日本語"
                            }
                        ],
                        "status": "Released",
                        "tagline": "Postwar Japan. From zero to minus.",
                        "title": "Godzilla Minus One",
                        "video": false,
                        "vote_average": 7.916,
                        "vote_count": 664
                    }"""
                )
        )

        // First call to fetch movies, should hit network and cache results
        val firstFetch = repository.getMovieDetails(940721)
        assert(firstFetch != null)

        // Modify the response to check if the cache is hit instead of network
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(
                    """{
                    "adult": false,
                    "backdrop_path": "/2GBiTa071pLSfPND1Iu7Pfk3fhy.jpg",
                    "belongs_to_collection": null,
                    "budget": 0,
                    "genres": [
                        {
                            "id": 10402,
                            "name": "Music"
                        },
                        {
                            "id": 18,
                            "name": "Drama"
                        },
                        {
                            "id": 36,
                            "name": "History"
                        }
                    ],
                    "homepage": "https://www.netflix.com/title/81705944",
                    "id": 1156966,
                    "imdb_id": "tt28690443",
                    "origin_country": [
                        "IT"
                    ],
                    "original_language": "it",
                    "original_title": "Sei nell'anima",
                    "overview": "The origin story of one of Italy's greatest rock stars, Gianna Nannini, who chased her dream despite obstacles from her family and the music industry.",
                    "popularity": 71.789,
                    "poster_path": "/tSrviCSaHBc0c8MHEydgr1eKXyB.jpg",
                    "production_companies": [
                        {
                            "id": 154744,
                            "logo_path": "/qYIEHRctL3YFth36F1LwhEQvOl5.png",
                            "name": "Rai Fiction",
                            "origin_country": "IT"
                        },
                        {
                            "id": 173293,
                            "logo_path": null,
                            "name": "Cassiopea",
                            "origin_country": ""
                        },
                        {
                            "id": 18156,
                            "logo_path": "/3nAuYcaWdpW0ULMvMz6V5HIJmCO.png",
                            "name": "Indiana Production",
                            "origin_country": "IT"
                        },
                        {
                            "id": 227848,
                            "logo_path": null,
                            "name": "La Toscana Film Commission",
                            "origin_country": ""
                        }
                    ],
                    "production_countries": [
                        {
                            "iso_3166_1": "IT",
                            "name": "Italy"
                        }
                    ],
                    "release_date": "2024-05-02",
                    "revenue": 0,
                    "runtime": 114,
                    "spoken_languages": [
                        {
                            "english_name": "Italian",
                            "iso_639_1": "it",
                            "name": "Italiano"
                        }
                    ],
                    "status": "Released",
                    "tagline": "",
                    "title": "Beautiful Rebel",
                    "video": false,
                    "vote_average": 6.9,
                    "vote_count": 15
                }"""
                )
        )

        // Set system time forward to still be within cache validity
        val secondFetch = repository.getMovieDetails(940721)

        // Assert that the cached data is returned instead of new network data
        assertTrue("Cached data expected but not found", secondFetch?.id == 940721)
    }
}