package com.example.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todolist.ToDo

@Database(entities = [ToDo::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class ToDoDatabase : RoomDatabase(){
    companion object{
        const val NAME = "ToDo_Database"
    }
    abstract fun getToDoDAO(): ToDoDAO
}