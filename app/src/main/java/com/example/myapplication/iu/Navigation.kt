package com.example.myapplication.iu

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.iu.viewmodel.DetailViewModel
import com.example.myapplication.iu.viewmodel.MainViewModel

@Composable
fun PokemonNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            val viewModel: MainViewModel = viewModel()
            MainScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(
            "detail/{pokemonId}",
            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt("pokemonId") ?: 1
            val viewModel: DetailViewModel = viewModel()
            DetailScreen(
                viewModel = viewModel,
                pokemonId = pokemonId
            )
        }
    }
}