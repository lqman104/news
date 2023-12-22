package com.luqman.news.core.model

import java.lang.Exception

sealed class Resource<T>(
    open val data: T? = null,
    open val error: ResourceText? = null
) {
    data class Success<T>(
        override val data: T?,
    ) : Resource<T>(data = data)

    data class Error<T>(
        override val error: ResourceText?,
        val exception: Exception? = null
    ) : Resource<T>(error = error)

    class Loading<T> : Resource<T>()
}
