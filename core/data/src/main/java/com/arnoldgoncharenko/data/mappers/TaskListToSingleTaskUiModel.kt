package com.arnoldgoncharenko.data.mappers

import com.arnoldgoncharenko.data.database.TaskState
import com.arnoldgoncharenko.data.model.SingleTaskUiModel
import com.arnoldgoncharenko.data.util.DateUtils
import com.arnoldgoncharenko.network.models.Task
import javax.inject.Inject

class TaskListToSingleTaskUiModel @Inject constructor(private val dateUtils: DateUtils) {
    fun map(networkTask: Task, taskState: TaskState): SingleTaskUiModel {
        return mapTask(networkTask, taskState)
    }

    private fun mapTask(
        task: Task,
        taskState: TaskState
    ): SingleTaskUiModel {
        return SingleTaskUiModel(
            taskTitle = task.Title,
            dueDate = task.DueDate,
            description = task.Description,
            daysLeft = dateUtils.calculateDaysLeft(task.DueDate, task.TargetDate),
            taskState = taskState
        )
    }
}