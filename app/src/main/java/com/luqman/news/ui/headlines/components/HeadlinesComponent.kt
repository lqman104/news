@file:OptIn(ExperimentalFoundationApi::class)

package com.luqman.news.ui.headlines.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luqman.news.data.model.News
import com.luqman.news.uikit.component.ImageComponent
import com.luqman.news.uikit.theme.AppTheme

@Composable
fun HeadlinesComponent(
    modifier: Modifier = Modifier,
    list: List<News>
) {
    val pagerState = rememberPagerState(pageCount = {
        list.size
    })
    HorizontalPager(
        modifier = modifier,
        state = pagerState
    ) { page ->
        val data = list[page]
        Box {
            ImageComponent(
                model = data.image,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(Color.White)
                    .drawWithCache {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = size.height / 5,
                            endY = size.height
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.Multiply)
                        }
                    }
            )

            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart),
                text = data.title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun HeadlinesComponentPrev() {
    AppTheme {
        HeadlinesComponent(
            list = listOf(
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
    }
}