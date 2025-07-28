package com.example.todolist.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AboutScreen(onList: () -> Unit) {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "ToDoList Aplikacija",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "ToDoList je jednostavna, ali učinkovita aplikacija za upravljanje svakodnevnim zadacima. Omogućuje korisnicima kreiranje više popisa, dodavanje i uređivanje stavki te jednostavno označavanje obavljenih zadataka.\n\n" +
                            "Cilj aplikacije je pomoći korisnicima da ostanu organizirani i produktivni kroz intuitivno i moderno korisničko sučelje.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(36.dp))

                Button(onClick = onList) {
                    Text("Start Creating")
                }
            }
        }
    }
}
