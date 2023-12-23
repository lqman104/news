package com.luqman.news.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.luqman.news.core.model.Resource
import com.luqman.news.data.model.DummyHelper
import com.luqman.news.ui.headlines.components.HeadlinesComponent
import com.luqman.news.ui.headlines.components.NewsPaginationComponent
import com.luqman.news.uikit.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    val viewModel: MainViewModel = viewModel()
                    MainScreen(
                        modifier = Modifier.fillMaxSize(),
                        state = viewModel.state,
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: MainScreenState
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
                )
            }

            NewsPaginationComponent(
                list = state.news.collectAsLazyPagingItems(),
                modifier = Modifier.wrapContentHeight(),
                snackBarHostState = snackBarHostState,
                onItemClicked = {}
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
                state = MainScreenState(
                    Resource.Success(
                        listOf(DummyHelper.NEWS)
                    )
                )
            )
        }
    }
}