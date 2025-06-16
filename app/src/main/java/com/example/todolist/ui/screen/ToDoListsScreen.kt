package com.example.todolist.ui.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.viewmodel.ToDoListsViewModel
import androidx.compose.foundation.layout.Spacer

@Composable
fun ToDoListsScreen(
    viewModel: ToDoListsViewModel,
    onListClick: (Int) -> Unit
) {
    val lists by viewModel.lists.observeAsState(emptyList())
    var title by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("New List Title") }
        )
        Button(onClick = {
            viewModel.addList(title)
            title = ""
        }) {
            Text("Add List")
        }

        Spacer(modifier = Modifier.padding(16.dp))

        LazyColumn {
            items(lists) { list ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable { onListClick(list.id) }
                        .padding(8.dp)
                ) {
                    Text(text = list.name, fontSize = 20.sp)
                }
            }
        }
    }
}



