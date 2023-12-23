package com.luqman.news.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luqman.news.data.model.DummyHelper
import com.luqman.news.data.model.News
import com.luqman.news.uikit.component.ImageComponent
import com.luqman.news.uikit.theme.AppTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeadlinesComponent(
    modifier: Modifier = Modifier,
    list: List<News>,
    onClickItem: (News) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = {
        list.size
    })
    LaunchedEffect(pagerState.currentPage) {
        try {
            delay(5000)
            if (pagerState.currentPage < 3) {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            } else {
                pagerState.animateScrollToPage(1, pageOffsetFraction = 0f)
            }
        } catch (_: Exception) {
        }
    }
    HorizontalPager(
        modifier = modifier,
        state = pagerState
    ) { page ->
        val data = list[page]
        Box(
            modifier = Modifier.clickable {
                onClickItem(data)
            }
        ) {
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

@Preview
@Composable
private fun HeadlinesComponentPrev() {
    AppTheme {
        HeadlinesComponent(
            list = listOf(DummyHelper.NEWS),
            onClickItem = {}
        )
    }
}