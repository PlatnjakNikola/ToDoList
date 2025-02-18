package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todolist.ToDo

@Dao
interface ToDoDAO {
    @Query("SELECT * FROM TODO ORDER BY createdAt DESC")
    fun getAllToDo() : LiveData<List<ToDo>>

    @Insert
    fun addToDo(todo:ToDo)

    @Query("DELETE FROM TODO WHERE id = :id")
    fun deleteToDo(id : Int)
}