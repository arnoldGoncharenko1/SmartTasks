package com.arnoldgoncharenko.data.di

import android.content.Context
import androidx.room.Room
import com.arnoldgoncharenko.data.database.TaskDatabase
import com.arnoldgoncharenko.data.database.TaskDbDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideTaskDatabase(@ApplicationContext context : Context): TaskDatabase =
        Room.databaseBuilder(context, TaskDatabase::class.java, "task.db")
            .build()

    @Provides
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDbDao {
        return taskDatabase.taskDao()
    }
}