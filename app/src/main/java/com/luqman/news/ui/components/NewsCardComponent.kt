package com.luqman.news.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luqman.news.core.helper.DateHelper.toDate
import com.luqman.news.data.model.DummyHelper
import com.luqman.news.data.model.News
import com.luqman.news.uikit.component.ImageComponent
import com.luqman.news.uikit.theme.AppTheme

@Composable
fun NewsCardComponent(
    news: News,
    modifier: Modifier = Modifier,
    onClickItem: (News) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClickItem(news)
            },
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Text(
                text = "${news.author} - ${news.source}",
                modifier = Modifier,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = news.title,
                modifier = Modifier,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = news.publishedAt.toDate(),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(Modifier.width(8.dp))
        ImageComponent(
            modifier = Modifier
                .width(110.dp)
                .heightIn(50.dp, 100.dp)
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(4.dp)),
            model = news.image,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun NewsCardComponentPrev() {
    AppTheme {
        NewsCardComponent(
            news = DummyHelper.NEWS,
            onClickItem = {})
    }
}