package com.arnoldgoncharenko.tasks.taskDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.arnoldgoncharenko.data.database.TaskState
import com.arnoldgoncharenko.data.model.SingleTaskUiModel
import com.arnoldgoncharenko.tasks.taskDetails.TaskDetailsViewModel.UiState
import com.arnoldgoncharenko.ui.theme.SmartTasksTheme
import com.houseravenstudios.ui.R

@Composable
internal fun TaskDetailsRoute(
    viewModel: TaskDetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    TaskDetailsScreen(
        state = viewModel.uiState,
        navigateBack = navigateBack,
        resolveTask = viewModel::resolveTask
    )
}

@Composable
internal fun TaskDetailsScreen(
    state: UiState,
    navigateBack: () -> Unit,
    resolveTask: (
        taskState: TaskState,
        comment: String
    ) -> Unit
) {
    when (state) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> TaskDetailsView(
            state,
            navigateBack,
            resolveTask
        )
    }
}

@Composable
internal fun LoadingScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.task_details_loading_description),
            fontSize = dimensionResource(id = R.dimen.font_24).value.sp
        )
    }
}

@Composable
internal fun TaskDetailsView(
    state: UiState.Success,
    navigateBack: () -> Unit,
    resolveTask: (taskState: TaskState, comment: String) -> Unit
) {
    var showCommentDialog by remember { mutableStateOf(false) }
    var showInputDialog by remember { mutableStateOf(false) }
    var newState by remember { mutableStateOf(state.data?.taskState) }

    if (showCommentDialog) {
        Dialog(onDismissRequest = { showCommentDialog = false }) {
            Surface(
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.task_details_dialog_radius)),
                color = colorResource(id = R.color.white)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.task_details_leave_comment_confirmation),
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_16))
                    )
                    Button(onClick = {
                        showInputDialog = true
                        showCommentDialog = false
                    }) {
                        Text(text = stringResource(id = R.string.yes))
                    }
                    Button(
                        onClick = {
                            showCommentDialog = false
                            resolveTask(newState ?: TaskState.UNRESOLVED, "")
                        },
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.margin_16))
                    ) {
                        Text(text = stringResource(id = R.string.no))
                    }
                }
            }
        }
    }

    if (showInputDialog) {
        Dialog(onDismissRequest = { showInputDialog = false }) {
            Surface(
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.task_details_dialog_radius)),
                color = colorResource(id = R.color.white)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var inputText by remember { mutableStateOf("") }
                    TextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        label = { Text(text = stringResource(id = R.string.task_details_enter_comment)) },
                        modifier = Modifier.padding(all = dimensionResource(id = R.dimen.margin_16))
                    )
                    Button(
                        onClick = {
                            showInputDialog = false
                            resolveTask(newState ?: TaskState.UNRESOLVED, inputText)
                        },
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.margin_16))
                    ) {
                        Text(text = stringResource(id = R.string.task_details_submit))
                    }
                }
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_chevron_left_24),
                        contentDescription = stringResource(id = R.string.task_details_go_back_content_description),
                        tint = colorResource(id = R.color.white)
                    )
                },
                onClick = {
                    navigateBack()
                }
            )
            Text(
                text = stringResource(id = R.string.task_details_title),
                color = colorResource(id = R.color.white)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.20f))
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_16)))
        Image(
            painter = painterResource(id = R.drawable.task_details),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.margin_16),
                    end = dimensionResource(id = R.dimen.margin_16)
                )
        )
        Surface(
            shape = RoundedCornerShape(
                bottomStart = dimensionResource(id = R.dimen.margin_8),
                bottomEnd = dimensionResource(id = R.dimen.margin_8)
            ),
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.margin_16),
                    end = dimensionResource(id = R.dimen.margin_16)
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.margin_16))
            ) {
                Text(
                    text = state.data?.taskTitle.orEmpty(),
                    style = MaterialTheme.typography.displayMedium.copy(
                        color = determineColor(state.data?.taskState),
                        fontSize = dimensionResource(id = R.dimen.font_24).value.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.primary,
                    thickness = dimensionResource(id = R.dimen.divider_thickness),
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.margin_8))
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = stringResource(id = R.string.due_date),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = colorResource(id = R.color.grey_yellow)
                            ),
                            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.margin_4))
                        )
                        Text(
                            text = state.data?.dueDate.orEmpty(),
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = determineColor(state.data?.taskState),
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = stringResource(id = R.string.days_left),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = colorResource(id = R.color.grey_yellow)
                            ),
                            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.margin_4))
                        )
                        Text(
                            text = state.data?.daysLeft.toString(),
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = determineColor(state.data?.taskState),
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.primary,
                    thickness = dimensionResource(id = R.dimen.divider_thickness),
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.margin_8))
                )
                Text(
                    text = state.data?.description.orEmpty(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = colorResource(id = R.color.grey_yellow)
                    )
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.primary,
                    thickness = dimensionResource(id = R.dimen.divider_thickness),
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.margin_8))
                )
                when (state.data?.taskState) {
                    TaskState.UNRESOLVED -> {
                        Text(
                            text = stringResource(id = R.string.task_details_unresolved),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = colorResource(id = R.color.unresolved_yellow),
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    TaskState.RESOLVED -> {
                        Text(
                            text = stringResource(id = R.string.task_details_resolved),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = colorResource(id = R.color.resolved_green),
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    else -> {
                        Text(
                            text = stringResource(id = R.string.task_details_unresolved),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }

        when (state.data?.taskState) {
            TaskState.UNRESOLVED -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(id = R.dimen.margin_16),
                            end = dimensionResource(id = R.dimen.margin_16),
                            top = dimensionResource(id = R.dimen.margin_4)
                        ),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        onClick = {
                            showCommentDialog = true
                            newState = TaskState.RESOLVED
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.resolved_green),
                            contentColor = colorResource(id = R.color.white)
                        ),
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(end = dimensionResource(id = R.dimen.margin_4)),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.task_list_corner_radius)),
                    ) {
                        Text(
                            text = stringResource(id = R.string.task_details_resolve),
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = colorResource(id = R.color.white),
                                fontSize = dimensionResource(id = R.dimen.font_16).value.sp
                            )
                        )
                    }

                    Button(
                        onClick = {
                            showCommentDialog = true
                            newState = TaskState.CANNOTRESOLVE
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = colorResource(id = R.color.white)
                        ),
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(start = dimensionResource(id = R.dimen.margin_4)),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.task_list_corner_radius)),

                        ) {
                        Text(
                            text = stringResource(id = R.string.task_details_cant_resolve),
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = colorResource(id = R.color.white),
                                fontSize = dimensionResource(id = R.dimen.font_16).value.sp
                            )
                        )
                    }
                }
            }

            TaskState.RESOLVED -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.task_details_icon_margin)))
                    Image(
                        painter = painterResource(id = R.drawable.sign_resolved),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.task_details_icon_margin)))
                }
            }

            else -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.task_details_icon_margin)))
                    Image(
                        painter = painterResource(id = R.drawable.unresolved_sign),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.task_details_icon_margin)))
                }
            }
        }
    }
}

@Composable
private fun determineColor(taskState: TaskState?): Color =
    if (taskState == TaskState.RESOLVED) {
        colorResource(id = R.color.resolved_green)
    } else {
        MaterialTheme.colorScheme.secondary
    }

@Preview
@Composable
fun TaskDetailsViewPreview() {
    SmartTasksTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            TaskDetailsView(
                state = UiState.Success(
                    data = SingleTaskUiModel(
                        taskTitle = "Task Title",
                        dueDate = "2012-02-03",
                        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eu gravida tortor. Aliquam pharetra, risus vitae finibus ultrices, neque turpis varius odio, et rhoncus diam elit posuere nisi. Donec suscipit, ante nec dictum tempus, enim massa aliquet nisl, sit amet dignissim felis odio tincidunt ex. Quisque varius massa id nulla imperdiet, ac bibendum risus cursus. In condimentum arcu tellus, in maximus tortor faucibus ut. Curabitur dignissim enim felis, at luctus justo pharetra a. Donec scelerisque, neque rhoncus euismod posuere, ex libero rhoncus mauris, in commodo neque nunc luctus dui. Nullam eros mauris, rhoncus ut odio quis, lacinia molestie leo. Curabitur eu blandit sapien. Praesent cursus tellus interdum vulputate sagittis.\n",
                        daysLeft = 12,
                        taskState = TaskState.UNRESOLVED
                    )
                ),
                navigateBack = {},
                resolveTask = { _, _ -> }
            )
        }
    }
}

@Preview
@Composable
fun TaskDetailsViewResolvedTaskPreview() {
    SmartTasksTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            TaskDetailsView(
                state = UiState.Success(
                    data = SingleTaskUiModel(
                        taskTitle = "Task Title",
                        dueDate = "2012-02-03",
                        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eu gravida tortor. Aliquam pharetra, risus vitae finibus ultrices, neque turpis varius odio, et rhoncus diam elit posuere nisi. Donec suscipit, ante nec dictum tempus, enim massa aliquet nisl, sit amet dignissim felis odio tincidunt ex. Quisque varius massa id nulla imperdiet, ac bibendum risus cursus. In condimentum arcu tellus, in maximus tortor faucibus ut. Curabitur dignissim enim felis, at luctus justo pharetra a. Donec scelerisque, neque rhoncus euismod posuere, ex libero rhoncus mauris, in commodo neque nunc luctus dui. Nullam eros mauris, rhoncus ut odio quis, lacinia molestie leo. Curabitur eu blandit sapien. Praesent cursus tellus interdum vulputate sagittis.\n",
                        daysLeft = 12,
                        taskState = TaskState.RESOLVED
                    )
                ),
                navigateBack = {},
                resolveTask = { _, _ -> }
            )
        }
    }
}

@Preview
@Composable
fun TaskDetailsViewCannotResolveTaskPreview() {
    SmartTasksTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            TaskDetailsView(
                state = UiState.Success(
                    data = SingleTaskUiModel(
                        taskTitle = "Task Title",
                        dueDate = "2012-02-03",
                        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eu gravida tortor. Aliquam pharetra, risus vitae finibus ultrices, neque turpis varius odio, et rhoncus diam elit posuere nisi. Donec suscipit, ante nec dictum tempus, enim massa aliquet nisl, sit amet dignissim felis odio tincidunt ex. Quisque varius massa id nulla imperdiet, ac bibendum risus cursus. In condimentum arcu tellus, in maximus tortor faucibus ut. Curabitur dignissim enim felis, at luctus justo pharetra a. Donec scelerisque, neque rhoncus euismod posuere, ex libero rhoncus mauris, in commodo neque nunc luctus dui. Nullam eros mauris, rhoncus ut odio quis, lacinia molestie leo. Curabitur eu blandit sapien. Praesent cursus tellus interdum vulputate sagittis.\n",
                        daysLeft = 12,
                        taskState = TaskState.CANNOTRESOLVE
                    )
                ),
                navigateBack = {},
                resolveTask = { _, _ -> }
            )
        }
    }
}

@Preview
@Composable
fun TaskDetailsViewEmptyPreview() {
    SmartTasksTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            TaskDetailsView(
                state = UiState.Success(
                    data = null
                ),
                navigateBack = {},
                resolveTask = { _, _ -> }
            )
        }
    }
}

@Preview
@Composable
fun TaskDetailsViewErrorPreview() {
    SmartTasksTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LoadingScreen()
        }
    }
}