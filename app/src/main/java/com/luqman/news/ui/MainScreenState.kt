package com.luqman.news.ui

import com.luqman.news.core.model.Resource
import com.luqman.news.data.model.News

data class MainScreenState(
    val headline: Resource<List<News>>? = null
)