package com.arnoldgoncharenko.tasks.taskList

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arnoldgoncharenko.data.model.TaskListUiModel
import com.arnoldgoncharenko.data.model.TaskUiModel
import com.arnoldgoncharenko.data.util.DateUtils
import com.arnoldgoncharenko.tasks.taskList.TaskListViewModel.Companion.ONE_DAY
import com.arnoldgoncharenko.tasks.taskList.TaskListViewModel.UiState.Failed
import com.arnoldgoncharenko.tasks.taskList.TaskListViewModel.UiState.Loading
import com.arnoldgoncharenko.tasks.taskList.TaskListViewModel.UiState.Success
import com.arnoldgoncharenko.ui.components.TaskListItem
import com.arnoldgoncharenko.ui.theme.SmartTasksTheme
import com.houseravenstudios.ui.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
internal fun TaskListRoute(
    viewModel: TaskListViewModel = hiltViewModel(),
    onTaskClicked: (String) -> Unit
) {
    TaskListScreen(
        state = viewModel.uiState,
        onTaskClicked = onTaskClicked,
        onDateChanged = viewModel::modifyDate,
        onRetryClicked = viewModel::retry
    )
}

@Composable
internal fun TaskListScreen(
    state: TaskListViewModel.UiState,
    onTaskClicked: (String) -> Unit,
    onDateChanged: (String, Int) -> String,
    onRetryClicked: () -> Unit
) {
    when(state) {
        is Failed -> ErrorScreen(onRetryClicked)
        is Loading -> LoadingScreen()
        is Success -> SuccessScreen(state, onTaskClicked, onDateChanged)
    }
}

@Composable
internal fun ErrorScreen(onRetryClicked: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.task_list_error_message))
        Button(onClick = {
            onRetryClicked()
        }) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
internal fun LoadingScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.task_details_icon_margin)))
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = null)
        Image(painter = painterResource(id = R.drawable.intro_illustration), contentDescription = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SuccessScreen(
    state: Success,
    onTaskClicked: (String) -> Unit,
    onDateChanged: (String, Int) -> String
) {
    var currentDate by rememberSaveable { mutableStateOf(state.currentDate) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        //Adding this so you can easily navigate to a set of dates
        //where you can see functionality.
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    val selectedDate = Date(datePickerState.selectedDateMillis ?: 0)
                    val dateFormatter = DateUtils.getDateFormatter()
                    dateFormatter.timeZone = TimeZone.getTimeZone("UTC")
                    currentDate = dateFormatter.format(selectedDate)

                    showDatePicker = false
                }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDatePicker = false
                }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }

    Column {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_chevron_left_24),
                        contentDescription = stringResource(id = R.string.task_list_last_date_content_description),
                        tint = colorResource(id = R.color.white)
                    )
                },
                onClick = {
                    currentDate = onDateChanged(currentDate, -ONE_DAY)
                }
            )
            if (currentDate == state.currentDate) {
                Text(
                    text = stringResource(id = R.string.task_list_today),
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.clickable {
                        showDatePicker = true
                    }
                )
            } else {
                Text(
                    text = currentDate,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.clickable {
                        showDatePicker = true
                    }
                )
            }
            IconButton(
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                        contentDescription = stringResource(id = R.string.task_list_next_date_content_description),
                        tint = colorResource(id = R.color.white)
                    )
                },
                onClick = {
                    currentDate = onDateChanged(currentDate, ONE_DAY)
                }
            )
        }

        val taskList = state.data.tasks.filter { it.targetDate == currentDate }
        if (taskList.isNotEmpty()) {
            taskList.forEach { task ->
                TaskListItem(
                    taskId = task.id,
                    taskTitle = task.title,
                    taskDueDate = task.dueDate.orEmpty(),
                    taskDaysLeft = task.daysLeft,
                    onTaskClicked = onTaskClicked,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.margin_8),
                        start = dimensionResource(id = R.dimen.margin_8),
                        end = dimensionResource(id = R.dimen.margin_8)
                    )
                )
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.matchParentSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.empty_screen),
                        contentDescription = stringResource(id = R.string.task_list_no_tasks_content_description)
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.task_list_loading_spacer_margin)))
                    Text(
                        stringResource(id = R.string.task_list_no_tasks_today),
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = colorResource(id = R.color.white),
                            fontSize = dimensionResource(id = R.dimen.font_24).value.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskListPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        SmartTasksTheme {
            SuccessScreen(
                state = Success(
                    data = TaskListUiModel(
                        tasks = listOf(
                            TaskUiModel("", "2024-08-25", "2024-08-25", "Test", 0),
                            TaskUiModel("", "2024-08-25", "2024-08-25", "Ultra long title that I will make sure doesn't break anything I hope", 0)
                        )
                    ),
                    currentDate = "2024-08-25"
                ),
                onTaskClicked = {},
                onDateChanged = { _, _ -> ""}
            )
        }
    }
}

@Preview
@Composable
fun TaskListNoTasksPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        SmartTasksTheme {
            SuccessScreen(
                state = Success(
                    data = TaskListUiModel(
                        tasks = listOf()
                    ),
                    currentDate = "2012-02-02"
                ),
                onTaskClicked = {},
                onDateChanged = { _, _ -> ""}
            )
        }
    }
}

@Preview
@Composable
fun TaskListLoadingPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        SmartTasksTheme {
            LoadingScreen()
        }
    }
}

@Preview
@Composable
fun TaskListErrorPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        SmartTasksTheme {
            ErrorScreen({})
        }
    }
}