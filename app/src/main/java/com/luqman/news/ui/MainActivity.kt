package com.luqman.news.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luqman.news.core.model.Resource
import com.luqman.news.data.model.News
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
                    MainScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: MainViewModel = viewModel()

    if (viewModel.state.headline is Resource.Success) {
        MainContent(
            list = viewModel.state.headline?.data.orEmpty(),
            modifier = modifier.fillMaxSize(),
            onQueryChange = {
            }
        )
    }
}

@Composable
fun MainContent(
    list: List<News>,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember {
        mutableStateOf("")
    }

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            onValueChange = {
                onQueryChange(it)
                query = it
            }
        )

        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            content = {
                items(list) {
                    Card(
                        shape = MaterialTheme.shapes.small,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = it.title,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun SnackBarComponentPreview() {
    AppTheme {
        Surface {
            MainContent(
                modifier = Modifier.fillMaxSize(),
                list = listOf(),
                onQueryChange = {}
            )
        }
    }
}