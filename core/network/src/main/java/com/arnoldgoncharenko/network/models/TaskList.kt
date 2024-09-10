package com.arnoldgoncharenko.network.models

import kotlinx.serialization.Serializable

@Serializable
data class TaskList(
    val tasks: List<Task>
)

@Serializable
data class Task(
    val id: String,
    val TargetDate: String,
    val DueDate: String?,
    val Title: String,
    val Description: String,
    val Priority: Int?
)

fun emptyTask(): Task = Task(
    id ="",
    TargetDate = "",
    DueDate = "",
    Title = "" ,
    Description = "",
    Priority = 0,
)