package com.luqman.news.data.repository

import com.luqman.news.core.model.Resource
import com.luqman.news.data.model.News

interface NewsDataSource {
    suspend fun getHeadline(): Resource<List<News>>
}