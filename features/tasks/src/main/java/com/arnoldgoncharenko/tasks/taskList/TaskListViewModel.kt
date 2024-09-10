package com.arnoldgoncharenko.tasks.taskList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoldgoncharenko.data.model.TaskListUiModel
import com.arnoldgoncharenko.data.util.DateUtils
import com.arnoldgoncharenko.domain.usecases.GetTaskListUseCase
import com.arnoldgoncharenko.network.service.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getTaskListUseCase: GetTaskListUseCase,
    private val dateUtils: DateUtils
) : ViewModel() {
    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            uiState = UiState.Loading
            getTaskListUseCase.fetchTasks().collectLatest(::handleTaskListResult)
        }
    }

    private fun handleTaskListResult(result: ApiResult<TaskListUiModel>) {
        uiState = when (result) {
            is ApiResult.Success -> UiState.Success(
                data = result.data,
                currentDate = dateUtils.getCurrentDate()
            )
            is ApiResult.Error -> UiState.Failed(message = result.throwable?.message.orEmpty())
        }
    }

    fun retry() {
        fetchTasks()
    }

    fun modifyDate(currentDate: String, increment: Int): String {
        return dateUtils.modifyDate(currentDate, increment)
    }

    sealed interface UiState {
        data object Loading : UiState
        data class Success(
            val data: TaskListUiModel,
            val currentDate: String,
        ) : UiState
        data class Failed(val message: String) : UiState
    }

    companion object {
        const val TASK_ID = "task_id"
        const val ONE_DAY = 86400000
    }
}

