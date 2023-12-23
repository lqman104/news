package com.luqman.news.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.luqman.news.domain.usecase.GetHeadlineUseCase
import com.luqman.news.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val headlineUseCase: GetHeadlineUseCase,
    private val newsUseCase: GetNewsUseCase,
) : ViewModel() {

    var state by mutableStateOf(MainScreenState())
        private set

    init {
        getHeadline()
        getNews()
    }

    private fun getHeadline() {
        viewModelScope.launch {
            headlineUseCase.invoke().collect {
                state = state.copy(headline = it)
            }
        }
    }

    private fun getNews() {
        val data = newsUseCase.invoke().cachedIn(viewModelScope)
        state = state.copy(news = data)
    }
}