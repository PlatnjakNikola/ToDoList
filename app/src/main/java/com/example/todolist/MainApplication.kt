package com.example.todolist

import android.app.Application
import androidx.room.Room
import com.example.todolist.database.ToDoDatabase

class MainApplication : Application() {
    companion object {
        lateinit var todoDatabase: ToDoDatabase
    }

    override fun onCreate(){
        super.onCreate()
        todoDatabase = Room.databaseBuilder(
            applicationContext,
            ToDoDatabase::class.java,
            ToDoDatabase.NAME
        ).build()
    }
}