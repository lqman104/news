package com.luqman.news.uikit.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.luqman.news.uikit.R
import com.luqman.news.uikit.theme.AppTheme

@Composable
fun ImageComponent(
    model: Any?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = model,
        contentDescription = null,
        placeholder = painterResource(R.drawable.ic_img),
        modifier = modifier
    )
}

@Preview
@Composable
fun ImageComponentPreview() {
    AppTheme {
        ImageComponent(model = null)
    }
}