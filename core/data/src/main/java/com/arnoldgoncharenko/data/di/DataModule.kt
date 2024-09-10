package com.arnoldgoncharenko.data.di

import com.arnoldgoncharenko.data.database.TaskDbDao
import com.arnoldgoncharenko.data.mappers.TaskListToSingleTaskUiModel
import com.arnoldgoncharenko.data.mappers.TaskListToTaskListUiModelMapper
import com.arnoldgoncharenko.data.repo.TasksRepository
import com.arnoldgoncharenko.network.service.TasksApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideTasksRepository(
        tasksApiService: TasksApiService,
        multiTaskMapper: TaskListToTaskListUiModelMapper,
        singleTaskMapper: TaskListToSingleTaskUiModel,
        taskDao: TaskDbDao
    ): TasksRepository =
        TasksRepository(tasksApiService, multiTaskMapper, singleTaskMapper, taskDao)
}