package com.luqman.news.data.services

import com.luqman.news.data.services.model.NewsHttpResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines?country=us&pageSize=3&page=1")
    suspend fun getHeadline(): NewsHttpResponse

    @GET("top-headlines?country=us")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsHttpResponse
}