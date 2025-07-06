package com.example.todolist.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.viewmodel.ToDoItemsViewModel


@Composable
fun ToDoItemsScreen(
    viewModel: ToDoItemsViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.items.observeAsState(emptyList())
    var input by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp, 30.dp)) {

        // Gumb za povratak
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = onBack) {
                Text("Back")
            }
        }


        // Unos novog itema
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("New List Title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (input.isNotBlank()) {
                        viewModel.addItem(input)
                        input = ""
                        keyboardController?.hide()
                    }
                }
            ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Dodavanje itema
        Button(
            onClick = {
                if (input.isNotBlank()) {
                    viewModel.addItem(input)
                    input = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Prikaz itema
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items) { item ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.toggleItemDone(item) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Text(
                                if (item.isDone) "✓" else "○",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Text(
                                text = item.content,
                                fontSize = 20.sp,
                                fontWeight = if (item.isDone) FontWeight.ExtraBold else FontWeight.Normal,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        IconButton(onClick = { viewModel.deleteItem(item.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }
    }
}
