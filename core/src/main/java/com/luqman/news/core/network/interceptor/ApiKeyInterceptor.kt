package com.luqman.news.core.network.interceptor

import com.luqman.news.core.network.ApiEndpoint.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

/*
* This the place to put your credential keys for the API
* */
class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val currentUrl = request().url
        proceed(
            request()
                .newBuilder()
                .url(currentUrl.newBuilder().addQueryParameter("apiKey", API_KEY).build())
                .build()
        )
    }
}