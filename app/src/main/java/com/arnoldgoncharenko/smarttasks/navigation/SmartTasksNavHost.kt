package com.arnoldgoncharenko.smarttasks.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.arnoldgoncharenko.tasks.navigation.navigateToTaskDetailsScreen
import com.arnoldgoncharenko.tasks.navigation.taskDetailsScreen
import com.arnoldgoncharenko.tasks.navigation.taskListScreen

@Composable
fun SmartTasksNavHost(
    navController: NavHostController,
    startDestination: String
) {
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            taskListScreen(navController::navigateToTaskDetailsScreen)
            taskDetailsScreen(navController::popBackStack)
        }
    }
}