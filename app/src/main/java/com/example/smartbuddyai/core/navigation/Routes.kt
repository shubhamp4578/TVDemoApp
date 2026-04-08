package com.example.smartbuddyai.core.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Player : Screen("player/{videoId}") {
        fun createRoute(videoId: String): String {
            return "player/${videoId}"
        }
    }

    object Quiz : Screen("quiz/{videoId}") {
        fun createRoute(videoId: String): String {
            return "quiz/${videoId}"
        }
    }

    object Result: Screen("result/{score}/{total}") {
        fun createRoute(score: Int, total: Int) = "result/$score/$total"
    }
}