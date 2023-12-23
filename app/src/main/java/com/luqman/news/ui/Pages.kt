package com.luqman.news.ui

import androidx.navigation.NavController
import com.luqman.news.data.model.News

sealed class Pages(open val route: String) {

    object Main : Pages("/main")
    object Detail : Pages("/detail") {
        const val DATA_KEY = "data"

        fun NavController.openDetail(news: News) {
            currentBackStackEntry?.savedStateHandle?.set(
                key = DATA_KEY, value = news
            )
            navigate(route)
        }
    }
}