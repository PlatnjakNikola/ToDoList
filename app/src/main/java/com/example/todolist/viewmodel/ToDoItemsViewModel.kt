package com.example.todolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.MainApplication
import com.example.todolist.entity.ToDoItemEntity
import kotlinx.coroutines.launch

class ToDoItemsViewModel(private val listId: Int) : ViewModel() {
    private val dao = MainApplication.todoDatabase.getToDoDAO()
    val items = dao.getItemsForList(listId).asLiveData()

    fun addItem(content: String) {
        viewModelScope.launch {
            dao.insertItem(ToDoItemEntity(listId = listId, content = content))
        }
    }

    fun deleteItem(itemId: Int) {
        viewModelScope.launch {
            dao.deleteItemById(itemId)
        }
    }

    fun toggleItemDone(item: ToDoItemEntity) {
        viewModelScope.launch {
            dao.updateItem(item.copy(isDone = !item.isDone))
        }
    }
}