package com.luqman.news.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import com.luqman.news.core.model.Resource
import com.luqman.news.data.model.DummyHelper
import com.luqman.news.data.model.News
import com.luqman.news.ui.components.HeadlinesComponent
import com.luqman.news.ui.components.NewsPaginationComponent
import com.luqman.news.uikit.theme.AppTheme


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: MainScreenState,
    onClickItem: (News) -> Unit
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.Top
        ) {
            if (state.headline is Resource.Success) {
                HeadlinesComponent(
                    list = state.headline.data.orEmpty(),
                    modifier = Modifier.wrapContentHeight(),
                    onClickItem = onClickItem
                )
            }

            NewsPaginationComponent(
                list = state.news.collectAsLazyPagingItems(),
                modifier = Modifier.wrapContentHeight(),
                snackBarHostState = snackBarHostState,
                onItemClicked = onClickItem
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    AppTheme {
        Surface {
            MainScreen(
                modifier = Modifier.fillMaxSize(),
                onClickItem = {},
                state = MainScreenState(
                    Resource.Success(
                        listOf(DummyHelper.NEWS)
                    )
                )
            )
        }
    }
}