package com.luqman.news.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.luqman.news.data.model.News
import com.luqman.news.ui.Pages.Detail.openDetail
import com.luqman.news.ui.screens.detail.DetailScreen
import com.luqman.news.ui.screens.main.MainScreen
import com.luqman.news.ui.screens.main.MainViewModel

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Pages.Main.route) {
        composable(Pages.Main.route) {
            val viewModel: MainViewModel = hiltViewModel()
            MainScreen(
                modifier = Modifier.fillMaxSize(),
                state = viewModel.state,
                onClickItem = {
                    navController.openDetail(it)
                }
            )
        }
        composable(Pages.Detail.route) {
            val backStack = navController.previousBackStackEntry?.savedStateHandle
            val news = backStack?.get<News>(Pages.Detail.DATA_KEY)

            if (news == null) {
                navController.navigateUp()
                return@composable
            }

            DetailScreen(
                news = news,
                modifier = Modifier.fillMaxSize(),
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }

}