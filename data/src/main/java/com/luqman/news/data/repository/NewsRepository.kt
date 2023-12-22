package com.luqman.news.data.repository

import com.luqman.news.core.model.Resource
import com.luqman.news.core.network.extension.ResponseExtension.runCatchingResponse
import com.luqman.news.data.model.News
import com.luqman.news.data.services.NewsService
import kotlinx.coroutines.CoroutineDispatcher

class NewsRepository(
    private val apiService: NewsService,
    private val coroutineDispatcher: CoroutineDispatcher
) : NewsDataSource {

    override suspend fun getHeadline(): Resource<List<News>> {
        return runCatchingResponse(coroutineDispatcher) {
            val data = apiService.getHeadline().articles.orEmpty().map {
                News(
                    source = it.source?.name.orEmpty(),
                    author = it.author.orEmpty(),
                    title = it.title.orEmpty(),
                    url = it.url.orEmpty(),
                    image = it.urlToImage.orEmpty(),
                    publishedAt = it.publishedAt.orEmpty(),
                    content = it.content.orEmpty(),
                )
            }

            Resource.Success(data)
        }
    }

}