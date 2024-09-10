package com.arnoldgoncharenko.data.model

import com.arnoldgoncharenko.data.database.TaskState

data class SingleTaskUiModel(
    val taskTitle: String,
    val dueDate: String?,
    val description: String,
    val daysLeft: Int,
    val taskState: TaskState
)