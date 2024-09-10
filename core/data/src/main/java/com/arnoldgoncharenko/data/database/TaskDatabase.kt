package com.arnoldgoncharenko.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskDbEntry::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDbDao
}