package com.arnoldgoncharenko.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskDbEntry(
    @PrimaryKey
    val id: String,
    val taskState: TaskState,
    val comment: String
)

enum class TaskState {
    UNRESOLVED, RESOLVED, CANNOTRESOLVE
}