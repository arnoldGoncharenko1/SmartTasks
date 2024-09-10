package com.arnoldgoncharenko.data.mappers

import com.arnoldgoncharenko.data.model.TaskListUiModel
import com.arnoldgoncharenko.data.model.TaskUiModel
import com.arnoldgoncharenko.data.util.DateUtils
import com.arnoldgoncharenko.network.models.Task
import com.arnoldgoncharenko.network.models.TaskList
import javax.inject.Inject

class TaskListToTaskListUiModelMapper @Inject constructor(private val dateUtils: DateUtils) {
    fun map(networkTaskList: TaskList): TaskListUiModel {
        return TaskListUiModel(
            tasks = mapTaskResults(networkTaskList.tasks)
        )
    }

    private fun mapTaskResults(taskList: List<Task>): List<TaskUiModel> {
        taskList.sortedBy { it.Priority }
        return buildList {
            taskList.forEach { taskItem ->
                add(TaskUiModel(
                    id = taskItem.id,
                    targetDate = taskItem.TargetDate,
                    dueDate = taskItem.DueDate,
                    title = taskItem.Title,
                    daysLeft = dateUtils.calculateDaysLeft(taskItem.DueDate, taskItem.TargetDate)
                ))
            }
        }
    }
}