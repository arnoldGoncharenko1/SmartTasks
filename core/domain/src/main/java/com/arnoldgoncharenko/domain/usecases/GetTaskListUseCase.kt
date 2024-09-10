package com.arnoldgoncharenko.domain.usecases

import com.arnoldgoncharenko.data.model.TaskListUiModel
import com.arnoldgoncharenko.data.repo.TasksRepository
import com.arnoldgoncharenko.network.service.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskListUseCase @Inject constructor(
    private val tasksRepo: TasksRepository
) {
    fun fetchTasks(): Flow<ApiResult<TaskListUiModel>> = tasksRepo.fetch()
}