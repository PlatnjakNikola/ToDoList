package com.example.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolist.entity.ToDoItemEntity
import com.example.todolist.entity.ToDoListEntity

@Database(
    entities = [ToDoListEntity::class, ToDoItemEntity::class],
    version = 2,
    exportSchema = true
)
abstract class ToDoDatabase : RoomDatabase(){
    companion object{
        const val NAME = "ToDo_Database"
    }
    abstract fun getToDoDAO(): ToDoDAO
}