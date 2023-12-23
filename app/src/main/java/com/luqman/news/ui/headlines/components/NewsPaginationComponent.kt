package com.luqman.news.ui.headlines.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Divider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.luqman.news.R
import com.luqman.news.core.model.asString
import com.luqman.news.core.network.exception.ApiException
import com.luqman.news.data.model.DummyHelper
import com.luqman.news.data.model.News
import com.luqman.news.uikit.component.ErrorScreenComponent
import com.luqman.news.uikit.component.LoadingComponent
import com.luqman.news.uikit.component.LoadingItemComponent
import com.luqman.news.uikit.theme.AppTheme
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun NewsPaginationComponent(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    list: LazyPagingItems<News>,
    onItemClicked: (News) -> Unit
) {
    val scope = rememberCoroutineScope()
    val actionButton = stringResource(id = R.string.retry_button)

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        handleFirstState(list.loadState.prepend) {
            list.retry()
        }

        handleFirstState(list.loadState.refresh) {
            list.retry()
        }

        items(count = list.itemCount) { index ->
            val item = list[index]
            if (item != null && item.source != "[Removed]") {
                NewsCardComponent(
                    news = item,
                    onClickItem = onItemClicked,
                    modifier = Modifier.padding(vertical = 8.dp)
                        .defaultMinSize(minHeight = 120.dp)
                )
                if (index != (list.itemCount - 1)) {
                    Divider()
                }
            }
        }

        handleAppendState(list.loadState.append) {
            scope.launch {
                val result = snackBarHostState.showSnackbar(
                    it,
                    duration = SnackbarDuration.Long,
                    actionLabel = actionButton
                )
                if (result == SnackbarResult.ActionPerformed) {
                    list.retry()
                }
            }
        }
    }
}


private fun LazyListScope.handleAppendState(state: LoadState, onError: (String) -> Unit) {
    item {
        when (state) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> LoadingItemComponent()
            is LoadState.Error -> onError(state.getErrorMessage())
        }
    }
}

private fun LazyListScope.handleFirstState(state: LoadState, onRetryClicked: () -> Unit) {
    item {
        val modifier = Modifier
            .fillMaxSize()
            .fillParentMaxSize()

        when (state) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> LoadingComponent(modifier)
            is LoadState.Error -> ErrorScreenComponent(
                showActionButton = true,
                actionButtonText = stringResource(id = R.string.retry_button),
                modifier = modifier,
                title = state.getTitleMessage(),
                message = state.getErrorMessage()
            ) {
                onRetryClicked()
            }
        }
    }
}

@Composable
private fun LoadState.Error.getErrorMessage(): String {
    return when (this.error) {
        is ApiException -> (this.error as ApiException).errorMessage.asString()
        else -> this.error.message.orEmpty()
    }
}

@Composable
private fun LoadState.Error.getTitleMessage(): String {
    return when (this.error) {
        is ApiException -> (this.error as ApiException).message.orEmpty()
        else -> stringResource(id = com.luqman.news.core.R.string.unknow_error_exception)
    }
}

@Preview(showBackground = true)
@Composable
fun NewsPaginationComponentPreview() {
    AppTheme {
        Surface {
            NewsPaginationComponent(
                modifier = Modifier.fillMaxSize(),
                list = flowOf(
                    PagingData.from(
                        listOf(
                            DummyHelper.NEWS
                        )
                    )
                ).collectAsLazyPagingItems(),
                snackBarHostState = SnackbarHostState(),
                onItemClicked = {},
            )
        }
    }
}