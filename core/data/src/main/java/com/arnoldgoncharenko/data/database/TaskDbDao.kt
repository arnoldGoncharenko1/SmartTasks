package com.arnoldgoncharenko.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDbDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskDbEntry)

    @Update
    suspend fun update(task: TaskDbEntry)

    @Query("SELECT * FROM tasks WHERE id = (:taskId)")
    suspend fun getTask(taskId: String): TaskDbEntry
}