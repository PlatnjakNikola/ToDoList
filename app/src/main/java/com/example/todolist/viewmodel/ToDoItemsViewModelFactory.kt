package com.example.todolist.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ToDoItemsViewModelFactory(private val listId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ToDoItemsViewModel(listId) as T
    }
}
