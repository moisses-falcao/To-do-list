package com.example.todolist.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.model.TaskModel

@Database(entities = [TaskModel::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object{
        private const val DATABASE_NAME: String = "Database_Name"

        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also{INSTANCE = it}
            }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java, DATABASE_NAME
            ).build()
    }
}