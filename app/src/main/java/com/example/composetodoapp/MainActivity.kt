package com.example.composetodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composetodoapp.ui.add_edit.compose.AddEditScreen
import com.example.composetodoapp.ui.all_todos.compose.AllToDoScreen
import com.example.composetodoapp.ui.theme.ComposeToDoAppTheme
import com.example.composetodoapp.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Routes.ALL_TODO_ROUTE) {
                composable(Routes.ALL_TODO_ROUTE) {
                    AllToDoScreen(
                        onNavigate = { event ->
                            navController.navigate(event.route)
                        })
                }
                composable(
                    route = Routes.ADD_EDIT_TODO_ROUTE + "?todoId={todoId}",
                    arguments = listOf(
                        navArgument(name = "todoId") {
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )
                    ) {
                    AddEditScreen(onPopBackStack = {
                        navController.popBackStack()
                    })
                }
            }
        }
    }
}