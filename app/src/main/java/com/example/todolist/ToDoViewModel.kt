package com.example.todolist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
class ToDoViewModel : ViewModel(){
    val todoDao = MainApplication.todoDatabase.getToDoDAO()
    val todoList : LiveData<List<ToDo>> = todoDao.getAllToDo()

    fun addToDo(title : String){
        viewModelScope.launch(Dispatchers.IO){
            todoDao.addToDo(ToDo(
                title = title, createdAt = Date.from(Instant.now()),
            ))
        }
    }

    fun deleteToDo(id : Int) {
        viewModelScope.launch( Dispatchers.IO){
            todoDao.deleteToDo(id)
        }

    }
}