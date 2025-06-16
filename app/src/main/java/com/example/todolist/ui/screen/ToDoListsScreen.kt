package com.example.todolist.ui.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ElevatedCard
import androidx.compose.ui.Alignment

@Composable
fun ToDoListsScreen(
    viewModel: ToDoListsViewModel,
    onListClick: (Int) -> Unit

) {
    val lists by viewModel.lists.observeAsState(emptyList())
    var title by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Unos novog naslova liste
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("New List Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Gumb za dodavanje
        Button(
            onClick = {
                if (title.isNotBlank()) {
                    viewModel.addList(title)
                    title = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add List")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Prikaz lista
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(lists) { list ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onListClick(list.id) }
                ) {
                    Text(
                        text = list.name,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}




