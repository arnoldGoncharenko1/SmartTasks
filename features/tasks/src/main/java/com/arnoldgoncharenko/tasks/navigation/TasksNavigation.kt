package com.arnoldgoncharenko.tasks.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.arnoldgoncharenko.tasks.taskDetails.TaskDetailsRoute
import com.arnoldgoncharenko.tasks.taskList.TaskListRoute
import com.arnoldgoncharenko.tasks.taskList.TaskListViewModel

const val TASK_LIST_ROUTE = "task_list_route"
const val TASK_DETAILS_ROUTE = "task_details_route"

private const val TASK_DETAILS_ARGS = "/{${TaskListViewModel.TASK_ID}}"
private const val TASK_DETAILS_NAVIGATION_ROUTE = TASK_DETAILS_ROUTE + TASK_DETAILS_ARGS

fun NavGraphBuilder.taskListScreen(onTaskClicked: (String) -> Unit) {
    composable(
        route = TASK_LIST_ROUTE
    ) {
        TaskListRoute(onTaskClicked = onTaskClicked)
    }
}

fun NavController.navigateToTaskDetailsScreen(id: String) {
    navigate("$TASK_DETAILS_ROUTE/$id")
}

fun NavGraphBuilder.taskDetailsScreen(navigateBack: () -> Unit) {
    composable(
        route = TASK_DETAILS_NAVIGATION_ROUTE,
        arguments = listOf(
            navArgument(TaskListViewModel.TASK_ID) { type = NavType.StringType }
        )
    ) {
        TaskDetailsRoute(navigateBack = navigateBack)
    }
}