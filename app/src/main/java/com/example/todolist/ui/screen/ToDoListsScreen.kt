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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color


@Composable
fun ToDoListsScreen(
    viewModel: ToDoListsViewModel,
    onListClick: (Int) -> Unit,
    onAboutClick: () -> Unit
) {
    val lists by viewModel.lists.observeAsState(emptyList())
    var searchQuery by remember { mutableStateOf("") } // za pretrazivanje
    var newListTitle by remember { mutableStateOf("") } // za novu listu
    // filtracija liste
    val filteredLists = lists.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    var showDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }
    var listToRename by remember { mutableStateOf<Int?>(null) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp, 32.dp)) {
        // about i add
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            // Gumb za odalzak na about screen
            Button(
                onClick = { onAboutClick() }
            ) {
                Text("About App")
            }

            // Gumb za dodavanje
            Button(onClick = { showDialog = true }) {
                Text("Add List")
            }
        }

        // Trazenje liste
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Search, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Search")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Prikaz lista
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filteredLists) { list ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onListClick(list.id) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = list.name,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(16.dp)
                        )

                        Row{
                            IconButton(onClick = {
                                showDialog = true
                                dialogText = list.name
                                listToRename = list.id
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Update")
                            }
                            IconButton(onClick = { viewModel.deleteList(list.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                }
            }
        }
        // popUp dialog
        if (showDialog) {
            val isRenaming = listToRename != null
            val inputText = if (isRenaming) dialogText else newListTitle
            val onTextChange: (String) -> Unit = if (isRenaming) {
                { newValue -> dialogText = newValue }
            } else {
                { newValue -> newListTitle = newValue }
            }
            AlertDialog(
                containerColor = Color(0xFF3F3E3E),
                onDismissRequest = {
                    showDialog = false
                    listToRename = null
                    newListTitle = ""
                    dialogText = ""
                },
                text = {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = onTextChange,
                        label = { Text(if (isRenaming) "New name" else "New list",
                            color = Color.White) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                    )
                },
                confirmButton = {
                    IconButton(
                        onClick = {
                            if (isRenaming) {
                                val listId = listToRename
                                if (listId != null && dialogText.isNotBlank()) {
                                    val list = lists.find { it.id == listId }
                                    if (list != null) {
                                        viewModel.renameList(list, dialogText.trim())
                                    }
                                }
                            } else {
                                if (newListTitle.isNotBlank()) {
                                    viewModel.addList(newListTitle.trim())
                                }
                            }
                            // Reset svega
                            showDialog = false
                            listToRename = null
                            dialogText = ""
                            newListTitle = ""
                        }) {
                        Icon(Icons.Default.Done, contentDescription = "Done", tint = MaterialTheme.colorScheme.tertiary )
                    }
                },
                dismissButton = {
                    IconButton(onClick = {
                        showDialog = false
                        listToRename = null
                        dialogText = ""
                        newListTitle = ""
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Cancel", tint = Color.Red)
                    }
                }
            )
        }
    }
}





