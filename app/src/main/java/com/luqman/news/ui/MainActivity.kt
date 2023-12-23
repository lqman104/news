package com.luqman.news.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.luqman.news.ui.headlines.components.HeadlinesComponent
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
                    MainScreen(modifier = Modifier.fillMaxSize(), state = viewModel.state)
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
        }
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
fun MainActivityPreview() {
    AppTheme {
        Surface {
            MainScreen(
                modifier = Modifier.fillMaxSize(),
                state = MainScreenState(
                    Resource.Success(
                        listOf(
                            News(
                                source = "Al Jazeera English",
                                author = "Justin Salhani",
                                title = "Beyond Gaza: How Yemen’s Houthis gain from attacking Red Sea ships - Al Jazeera English",
                                url = "https://www.aljazeera.com/features/2023/12/22/beyond-gaza-how-yemens-houthis-gain-from-attacking-red-sea-ships",
                                image = "https://www.aljazeera.com/wp-content/uploads/2023/12/AP23325303923199-1-1703222472.jpg?resize=1620%2C1080&quality=80",
                                publishedAt = "2023-12-22T05:51:53Z",
                                content = "Lorem ipsum dolor sis amet",
                            )
                        )
                    )
                )
            )
        }
    }
}