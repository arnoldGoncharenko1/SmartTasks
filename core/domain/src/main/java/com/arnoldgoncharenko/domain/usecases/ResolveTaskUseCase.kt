package com.arnoldgoncharenko.domain.usecases

import com.arnoldgoncharenko.data.database.TaskState
import com.arnoldgoncharenko.data.repo.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResolveTaskUseCase @Inject constructor(
    private val tasksRepo: TasksRepository
) {
    fun resolveTask(
        id: String,
        taskState: TaskState,
        comment: String
    ): Flow<TaskState> = tasksRepo.resolveTask(
        id,
        taskState,
        comment
    )
}