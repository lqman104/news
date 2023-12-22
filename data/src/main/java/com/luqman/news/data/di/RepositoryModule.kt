package com.luqman.news.data.di

import com.luqman.news.data.repository.NewsDataSource
import com.luqman.news.data.repository.NewsRepository
import com.luqman.news.data.services.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): NewsService = retrofit.create(NewsService::class.java)

    @Provides
    fun provideRepository(
        apiService: NewsService
    ): NewsDataSource = NewsRepository(apiService, Dispatchers.IO)
}