package com.arnoldgoncharenko.domain.usecases

import com.arnoldgoncharenko.data.model.SingleTaskUiModel
import com.arnoldgoncharenko.data.model.TaskUiModel
import com.arnoldgoncharenko.data.repo.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val tasksRepo: TasksRepository
) {
    fun fetchSpecificTask(id: String): Flow<SingleTaskUiModel?> = tasksRepo.fetchSpecificTask(id)
}