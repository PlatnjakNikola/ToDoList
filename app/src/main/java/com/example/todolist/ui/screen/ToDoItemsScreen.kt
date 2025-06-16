package com.example.todolist.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.entity.ToDoItemEntity
import com.example.todolist.viewmodel.ToDoItemsViewModel

@Composable
fun ToDoItemsScreen(
    viewModel: ToDoItemsViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.items.observeAsState(emptyList())
    var input by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        Button(onClick = onBack) {
            Text("Back")
        }

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("New Item") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            viewModel.addItem(input)
            input = ""
        }) {
            Text("Add Item")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(items) { item ->
                ItemRow(item = item, onToggle = {
                    viewModel.toggleItemDone(it)
                }, onDelete = {
                    viewModel.deleteItem(it.id)
                })
            }
        }
    }
}

@Composable
fun ItemRow(
    item: ToDoItemEntity,
    onToggle: (ToDoItemEntity) -> Unit,
    onDelete: (ToDoItemEntity) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onToggle(item) },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text(
                if (item.isDone) "✓ " else "○ ",
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(item.content, fontSize = 20.sp)
        }
        IconButton(onClick = { onDelete(item) }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}
