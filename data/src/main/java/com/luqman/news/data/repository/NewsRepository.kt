package com.luqman.news.data.repository

import com.luqman.news.core.model.Resource
import com.luqman.news.core.network.extension.ResponseExtension.runCatchingResponse
import com.luqman.news.data.model.News
import com.luqman.news.data.services.NewsService
import com.luqman.news.data.services.model.ArticlesItem
import kotlinx.coroutines.CoroutineDispatcher

class NewsRepository(
    private val apiService: NewsService,
    private val coroutineDispatcher: CoroutineDispatcher
) : NewsDataSource {

    override suspend fun getHeadline(): Resource<List<News>> {
        return runCatchingResponse(coroutineDispatcher) {
            val data = apiService.getHeadline().articles.orEmpty().map {
                it.toNews()
            }

            Resource.Success(data)
        }
    }

    override suspend fun getNews(page: Int): Resource<List<News>> {
        return runCatchingResponse(coroutineDispatcher) {
            val data = apiService.getNews(page = page, pageSize = 15).articles.orEmpty().map {
                it.toNews()
            }

            Resource.Success(data)
        }
    }

    private fun ArticlesItem.toNews(): News = News(
        source = source?.name.orEmpty(),
        author = author.orEmpty(),
        title = title.orEmpty(),
        url = url.orEmpty(),
        image = urlToImage.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        content = content.orEmpty(),
    )
}