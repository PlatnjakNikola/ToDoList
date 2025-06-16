package com.example.todolist.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="todo_lists")
data class ToDoListEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)