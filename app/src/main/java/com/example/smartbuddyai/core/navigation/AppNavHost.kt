package com.example.smartbuddyai.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartbuddyai.feature.home.HomeScreen
import com.example.smartbuddyai.feature.home.HomeViewModel
import com.example.smartbuddyai.feature.home.HomeViewModelFactory
import com.example.smartbuddyai.feature.player.PlayerScreen
import com.example.smartbuddyai.feature.quiz.QuizScreen
import com.example.smartbuddyai.feature.quiz.QuizViewModel
import com.example.smartbuddyai.feature.quiz.ResultScreen
import com.example.smartbuddyai.feature.search.SearchScreen
import com.example.smartbuddyai.feature.search.SearchViewModel

@Composable
fun AppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            val context = LocalContext.current
            val viewModel: HomeViewModel = viewModel(
                factory = HomeViewModelFactory(context)
            )
            HomeScreen(
                viewModel = viewModel,
                onVideoClick = { source ->
                    navHostController.navigate(Screen.Player.createRoute(source))
                }
            )
        }
        composable(Screen.Search.route) {
            val viewModel: SearchViewModel = viewModel()
            SearchScreen(
                viewModel = viewModel,
                onItemClicked = { videoId ->
                    navHostController.navigate(Screen.Player.createRoute(videoId))
                }
            )
        }
        composable(Screen.Player.route) { backStackEntry ->
            val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
            PlayerScreen(
                videoId,
                onVideoEnd = {
                    navHostController.navigate(Screen.Quiz.createRoute(it))
                })
        }

        composable(
            route = Screen.Quiz.route
        ) { backStackEntry ->
            val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
            val viewModel: QuizViewModel = viewModel()
            QuizScreen(
                videoId = videoId,
                viewModel = viewModel,
                onQuizFinished = {
                    navHostController.navigate(
                        Screen.Result.createRoute(
                            viewModel.score,
                            viewModel.totalQuestions
                        )
                    )
                }
            )

        }

        composable(
            route = Screen.Result.route
        ) { backstackEntry ->
            val score = backstackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            val total = backstackEntry.arguments?.getString("total")?.toIntOrNull() ?: 0

            ResultScreen(
                score = score,
                total = total,
                onRetry = {
                    navHostController.popBackStack()
                },
                onContinue = {
                    navHostController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}