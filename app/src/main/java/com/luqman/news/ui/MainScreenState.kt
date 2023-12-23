package com.luqman.news.ui

import androidx.paging.PagingData
import com.luqman.news.core.model.Resource
import com.luqman.news.data.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class MainScreenState(
    val headline: Resource<List<News>>? = null,
    val news: Flow<PagingData<News>> = flow {  }
)