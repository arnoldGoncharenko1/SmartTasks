package com.arnoldgoncharenko.tasks.taskDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoldgoncharenko.data.database.TaskState
import com.arnoldgoncharenko.data.model.SingleTaskUiModel
import com.arnoldgoncharenko.data.model.TaskUiModel
import com.arnoldgoncharenko.domain.usecases.GetTaskUseCase
import com.arnoldgoncharenko.domain.usecases.ResolveTaskUseCase
import com.arnoldgoncharenko.tasks.taskList.TaskListViewModel.Companion.TASK_ID
import com.arnoldgoncharenko.tasks.taskList.TaskListViewModel.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskUseCase,
    private val resolveTaskUseCase: ResolveTaskUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    init {
        fetchTask()
    }

    private fun fetchTask() {
        val id = savedStateHandle.get<String>(TASK_ID).orEmpty()
        viewModelScope.launch {
            uiState = UiState.Loading
            getTaskUseCase.fetchSpecificTask(id = id).collectLatest(::handleFoundTaskResult)
        }
    }

    private fun handleFoundTaskResult(result: SingleTaskUiModel?) {
        uiState = UiState.Success(data = result)
    }

    fun resolveTask(taskState: TaskState, comment: String) {
        val id = savedStateHandle.get<String>(TASK_ID).orEmpty()
        viewModelScope.launch {
            resolveTaskUseCase.resolveTask(
                id = id,
                taskState = taskState,
                comment = comment
            ).collectLatest(::handleTaskResolveResult)
        }
    }

    private fun handleTaskResolveResult(result: TaskState) {
        uiState = UiState.Success(
            data = (uiState as UiState.Success).data?.copy(
                taskState = result
            )
        )
    }

    sealed interface UiState {
        data object Loading : UiState
        data class Success(val data: SingleTaskUiModel?) : UiState
    }
}