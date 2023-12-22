package com.luqman.news.domain.usecase

import com.luqman.news.core.model.Resource
import com.luqman.news.data.model.News
import com.luqman.news.data.repository.NewsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHeadlineUseCase @Inject constructor(
    private val repository: NewsDataSource
) {

    suspend operator fun invoke(): Flow<Resource<List<News>>> = flow {
        emit(Resource.Loading())
        emit(repository.getHeadline())
    }
}