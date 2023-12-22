package com.luqman.news.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.news.core.model.Resource
import com.luqman.news.domain.usecase.GetHeadlineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetHeadlineUseCase,
) : ViewModel() {

    var state by mutableStateOf(MainScreenState())
        private set

    init {
        getHeadline()
    }

    private fun getHeadline() {
        viewModelScope.launch {
            useCase.invoke().collect {
                state = state.copy(headline = it)
            }
        }
    }
}