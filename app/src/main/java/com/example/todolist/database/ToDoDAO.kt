package com.example.todolist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.entity.ToDoItemEntity
import com.example.todolist.entity.ToDoListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDAO {

    //LISTE
    @Query("SELECT * FROM todo_lists ORDER BY id DESC")
    fun getAllLists(): Flow<List<ToDoListEntity>>

    @Insert
    suspend fun insertList(list: ToDoListEntity)

    @Update
    suspend fun updateList(list: ToDoListEntity)

    @Query("DELETE FROM todo_lists WHERE id = :listId")
    suspend fun deleteListById(listId: Int)


    //STAVKE
    @Query("SELECT * FROM todo_items WHERE listId = :listId ORDER BY id DESC")
    fun getItemsForList(listId: Int): Flow<List<ToDoItemEntity>>

    @Insert
    suspend fun insertItem(item: ToDoItemEntity)

    @Update
    suspend fun updateItem(item: ToDoItemEntity)

    @Query("DELETE FROM todo_lists WHERE id = :itemId")
    suspend fun deleteItemById(itemId: Int)

}