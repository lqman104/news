package com.luqman.news.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.luqman.news.data.model.News
import com.luqman.news.data.repository.NewsDataSource
import com.luqman.news.data.repository.NewsPagingDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsDataSource
) {

    operator fun invoke(): Flow<PagingData<News>> {
        return Pager(
            pagingSourceFactory = { NewsPagingDataSource(repository) },
            config = PagingConfig(pageSize = KEY_LIMIT, initialLoadSize = KEY_LIMIT),
            initialKey = 1
        ).flow
    }

    companion object {
        private const val KEY_LIMIT = 15
    }
}