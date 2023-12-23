package com.luqman.news.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.luqman.news.R
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
    val newsList = state.news.collectAsLazyPagingItems()
    val localDensity = LocalDensity.current
    var headlineHeight by remember {
        mutableStateOf(260.dp)
    }
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val scrollState = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                headlineHeight += with(localDensity) { consumed.y.toDp() }
                return super.onPostScroll(consumed, available, source)
            }
        }
    }

    Scaffold(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .nestedScroll(scrollState),
            verticalArrangement = Arrangement.Top
        ) {
            if (state.headline is Resource.Success) {
                HeadlinesComponent(
                    list = state.headline.data.orEmpty(),
                    modifier = Modifier.height(headlineHeight),
                    onClickItem = onClickItem
                )
            }

            if (0 < newsList.itemCount) {
                Text(
                    text = stringResource(id = R.string.news_section_title),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(
                        top = 24.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                )
            }

            NewsPaginationComponent(
                list = newsList,
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