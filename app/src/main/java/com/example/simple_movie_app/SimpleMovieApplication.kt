package com.example.simple_movie_app

import android.app.Application

class SimpleMovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalVariables.setApplicationContext(this.applicationContext)
        GlobalVariables.setServerEndpoint(BuildConfig.SERVER_ENDPOINT)
        GlobalVariables.setApiKey(BuildConfig.SERVER_API_KEY)
    }
}