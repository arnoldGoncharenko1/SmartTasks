package com.arnoldgoncharenko.data.model

data class TaskListUiModel(
    val tasks: List<TaskUiModel>
)

data class TaskUiModel(
    val id: String,
    val targetDate: String,
    val dueDate: String?,
    val title: String,
    val daysLeft: Int?
)