package com.luqman.news.core.model

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: ResourceText? = null
)