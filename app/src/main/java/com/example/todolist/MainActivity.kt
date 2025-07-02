package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.ui.screen.ToDoItemsScreen
import com.example.todolist.ui.screen.ToDoListsScreen
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.viewmodel.ToDoItemsViewModel
import com.example.todolist.viewmodel.ToDoListsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toDoListsViewModel = ViewModelProvider(this)[ToDoListsViewModel::class.java]

        setContent {
            ToDoListTheme {
                val navController: NavHostController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "lists"
                ) {
                    composable("lists") {
                        ToDoListsScreen(
                            viewModel = toDoListsViewModel,
                            onListClick = { listId ->
                                navController.navigate("items/$listId")
                            }
                        )
                    }

                    composable("items/{listId}") { backStackEntry ->
                        val listId = backStackEntry.arguments?.getString("listId")?.toIntOrNull()
                            ?: return@composable

                        val toDoItemsViewModel: ToDoItemsViewModel = viewModel(
                            factory = viewModelFactory {
                                initializer {
                                    ToDoItemsViewModel(listId)
                                }
                            }
                        )
                        ToDoItemsScreen(
                            viewModel = toDoItemsViewModel,
                            onBack = { navController.popBackStack() },
                        )
                    }
                }
            }
        }
    }
}
