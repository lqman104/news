package com.luqman.news.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val source: String,
    val author: String,
    val title: String,
    val url: String,
    val image: String,
    val publishedAt: String,
    val content: String
): Parcelable
