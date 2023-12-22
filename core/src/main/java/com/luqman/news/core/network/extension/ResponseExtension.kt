package com.luqman.news.core.network.extension

import com.luqman.news.core.R
import com.luqman.news.core.model.Resource
import com.luqman.news.core.model.ResourceText
import com.luqman.news.core.network.exception.ApiException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

object ResponseExtension {
    suspend fun <T> runCatchingResponse(
        dispatcher: CoroutineDispatcher,
        action: suspend () -> Resource<T>
    ): Resource<T> {
        return withContext(dispatcher) {
            try {
                action()
            } catch (e: Exception) {
                if (e is ApiException) {
                    Resource.Error(
                        error = e.errorMessage
                    )
                } else {
                    e.message?.let {
                        Resource.Error(error = ResourceText.Plain(message = it), exception = e)
                    } ?: Resource.Error(
                        error = ResourceText.StringId(resId = R.string.unknow_error_exception),
                        exception = e
                    )

                }
            }
        }
    }

    suspend fun <T> runCatchingResponse(
        dispatcher: CoroutineDispatcher,
        action: suspend () -> T,
        default: suspend () -> T
    ): T {
        return withContext(dispatcher) {
            try {
                action()
            } catch (e: Exception) {
                default()
            }
        }
    }
}