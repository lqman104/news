package com.luqman.news.data.services

import com.luqman.news.data.services.model.NewsHttpResponse
import retrofit2.http.GET

interface NewsService {

    @GET("top-headlines?country=us&pageSize=3&page=1")
    suspend fun getHeadline(): NewsHttpResponse
}