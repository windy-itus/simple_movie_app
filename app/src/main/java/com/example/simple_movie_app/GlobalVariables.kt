package com.example.simple_movie_app

import android.content.Context
import java.lang.ref.WeakReference

internal object GlobalVariables {
    private var applicationContextReference: WeakReference<Context>? = null
    private var serverEndpoint: String? = null
    private var apiKey: String? = null

    fun setApplicationContext(context: Context) {
        val applicationContext = context.applicationContext ?: return

        applicationContextReference = WeakReference(applicationContext)
    }

    fun getApplicationContext(): Context? {
        return applicationContextReference?.get()
    }

    fun setServerEndpoint(endpoint: String) {
        serverEndpoint = endpoint
    }

    fun getServerEndpoint(): String? {
        return serverEndpoint
    }

    fun setApiKey(apiKey: String) {
        this.apiKey = apiKey
    }

    fun getApiKey(): String? {
        return apiKey
    }
}