package com.example.simple_movie_app.data.network

import com.example.simple_movie_app.GlobalVariables

internal object ApiServiceManager {
    internal var tMDBApiService: TMDBApiService? = null
        private set
        get() {
            if (field == null) {
                val baseUrl = GlobalVariables.getServerEndpoint()
                    ?: throw IllegalStateException("Base url is not set. Please double check.")
                field = ServiceBuilder()
                    .setBaseUrl(baseUrl)
                    .createService(TMDBApiService::class.java)
            }

            return field
        }
}