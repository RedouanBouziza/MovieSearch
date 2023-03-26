package com.example.madlevel6task2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.madlevel6task2.ui.screens.MovieDetail
import com.example.madlevel6task2.ui.screens.MovieList
import com.example.madlevel6task2.ui.screens.MovieScreens
import com.example.madlevel6task2.viewmodel.MovieViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold() { innerPadding ->
                NavHostScreen(navController, innerPadding)
            }
        }
    }
}

@Composable
private fun NavHostScreen(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: MovieViewModel = viewModel()
) {
    NavHost(
        navController,
        startDestination = MovieScreens.MovieListScreen.route,
        Modifier.padding(innerPadding)
    ) {
        composable(MovieScreens.MovieListScreen.route) {
            MovieList(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(MovieScreens.MovieDetailScreen.route) {
            MovieDetail(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}
