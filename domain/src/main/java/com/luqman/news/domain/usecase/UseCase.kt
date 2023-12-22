package com.luqman.news.domain.usecase

import com.luqman.news.core.model.ResourceText
import com.luqman.news.core.model.ValidationResult
import com.luqman.news.domain.R

class UseCase {

    operator fun invoke(input: String): ValidationResult {
        return when {
            input.isEmpty() -> ValidationResult(
                successful = false,
                errorMessage = ResourceText.StringId(R.string.data_empty_error)
            )
            else -> {
                ValidationResult(
                    successful = true
                )
            }
        }
    }
}