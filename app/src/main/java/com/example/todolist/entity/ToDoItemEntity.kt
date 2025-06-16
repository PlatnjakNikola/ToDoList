package com.example.todolist.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class ToDoItemEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    val listId: Int,
    var content : String,
    var isDone : Boolean = false
)

