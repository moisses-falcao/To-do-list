package com.example.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.ui.MainActivity


@Entity(tableName = "TaskModel")
data class TaskModel(

    var title: String,
    var date: String,
    var hour: String,
    var description: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
