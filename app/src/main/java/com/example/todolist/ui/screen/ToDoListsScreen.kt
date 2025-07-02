package com.example.todolist.ui.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment

@Composable
fun ToDoListsScreen(
    viewModel: ToDoListsViewModel,
    onListClick: (Int) -> Unit
) {
    val lists by viewModel.lists.observeAsState(emptyList())
    var title by remember { mutableStateOf("") }

    // Novo: stanje za uređivanje
    var editingListId by remember { mutableStateOf<Int?>(null) }
    var editedTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 32.dp)
    ) {

        // Unos nove liste
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

        Spacer(modifier = Modifier.height(8.dp))

        // Prikaz svih lista
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(lists) { list ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (editingListId == list.id) {
                            // Ako uređujemo ovu listu – prikaži input polje
                            OutlinedTextField(
                                value = editedTitle,
                                onValueChange = { editedTitle = it },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            IconButton(onClick = {
                                viewModel.renameList(list, editedTitle)
                                editingListId = null
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Confirm Edit")
                            }
                        } else {
                            // Inače prikaži naziv kao tekst
                            Text(
                                text = list.name,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onListClick(list.id) }
                            )
                            Row {
                                IconButton(onClick = {
                                    editingListId = list.id
                                    editedTitle = list.name
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                                }
                                IconButton(onClick = { viewModel.deleteList(list.id) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


