package com.luqman.news.data.model

data class News(
    val source: String,
    val author: String,
    val title: String,
    val url: String,
    val image: String,
    val publishedAt: String,
    val content: String
)
