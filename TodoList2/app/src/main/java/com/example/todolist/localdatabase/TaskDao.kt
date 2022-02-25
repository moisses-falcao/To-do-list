package com.example.todolist.localdatabase

import androidx.room.*
import com.example.todolist.model.TaskModel

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (taskmodel: TaskModel)

    @Delete
    suspend fun delete (taskmodel: TaskModel)
/*
    @Query("SELECT * FROM TaskModel WHERE id = :taskModelId")
    suspend fun search(taskModelId: Int): TaskModel

     @Update
    suspend fun update (taskmodel: TaskModel)
*/
    @Query("SELECT * FROM TaskModel")
    fun getAllTasks() : List<TaskModel>

}