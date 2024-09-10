package com.arnoldgoncharenko.data.repo

import com.arnoldgoncharenko.data.database.TaskDbDao
import com.arnoldgoncharenko.data.database.TaskDbEntry
import com.arnoldgoncharenko.data.database.TaskState
import com.arnoldgoncharenko.data.mappers.TaskListToSingleTaskUiModel
import com.arnoldgoncharenko.data.mappers.TaskListToTaskListUiModelMapper
import com.arnoldgoncharenko.data.model.SingleTaskUiModel
import com.arnoldgoncharenko.data.model.TaskListUiModel
import com.arnoldgoncharenko.network.models.TaskList
import com.arnoldgoncharenko.network.models.emptyTask
import com.arnoldgoncharenko.network.service.ApiResult
import com.arnoldgoncharenko.network.service.TasksApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TasksRepository @Inject constructor(
    private val tasksApiService: TasksApiService,
    private val multiTaskMapper: TaskListToTaskListUiModelMapper,
    private val singleTaskMapper: TaskListToSingleTaskUiModel,
    private val taskDao: TaskDbDao
) {
    private var taskListUiModelCache : TaskList? = null

    fun fetch(): Flow<ApiResult<TaskListUiModel>> {
        return flow<ApiResult<TaskListUiModel>> {
            val result: TaskList?

            if (taskListUiModelCache != null) {
                result = taskListUiModelCache
            } else {
                result = tasksApiService.getTasks()
                taskListUiModelCache = result
                result.tasks.forEach { task ->
                    taskDao.insert(task = TaskDbEntry(task.id, TaskState.UNRESOLVED, ""))
                }
            }

            emit(ApiResult.Success(data = multiTaskMapper.map(result ?: TaskList(emptyList()))))
        }.catch {
            emit(ApiResult.Error(it))
        }
    }

    fun fetchSpecificTask(id: String): Flow<SingleTaskUiModel?> {
        return flow<SingleTaskUiModel> {
            val foundTask = taskListUiModelCache?.tasks?.find { task -> task.id == id }
            val singleTaskUiModel = singleTaskMapper.map(
                networkTask = foundTask ?: emptyTask(),
                taskState = taskDao.getTask(taskId = foundTask?.id.orEmpty()).taskState
            )
            emit(singleTaskUiModel)
        }
    }

    fun resolveTask(
        id: String,
        taskState: TaskState,
        comment: String
    ): Flow<TaskState> {
        return flow {
            val retrievedTask = taskDao.getTask(taskId = id)
            taskDao.update(
                task = TaskDbEntry(
                    retrievedTask.id,
                    taskState,
                    comment
                )
            )
            emit(taskState)
        }
    }
}