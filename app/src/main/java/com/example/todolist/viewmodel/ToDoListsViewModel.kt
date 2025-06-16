package com.example.todolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.MainApplication
import com.example.todolist.entity.ToDoListEntity
import kotlinx.coroutines.launch

class ToDoListsViewModel : ViewModel() {
    private val dao = MainApplication.todoDatabase.getToDoDAO()
    val lists = dao.getAllLists().asLiveData()

    fun addList(name: String) {
        viewModelScope.launch {
            dao.insertList(ToDoListEntity(name = name))
        }
    }

    fun deleteList(listId: Int) {
        viewModelScope.launch {
            dao.deleteListById(listId)
        }
    }

    fun renameList(list: ToDoListEntity, newName: String) {
        viewModelScope.launch {
            dao.updateList(list.copy(name = newName))
        }
    }
}
