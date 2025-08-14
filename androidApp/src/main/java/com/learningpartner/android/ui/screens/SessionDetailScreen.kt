package com.learningpartner.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.learningpartner.android.ui.viewmodels.StudentViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDetailScreen() {
    val studentViewModel: StudentViewModel = koinViewModel()
    val session by studentViewModel.selectedSession.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Session Details") },
                navigationIcon = {
                    IconButton(onClick = { studentViewModel.clearSelectedSession() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (session == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = session!!.classSession.name, style = MaterialTheme.typography.headlineSmall)
                    Text(text = "Status: ${session!!.processingStatus}")
                    session!!.content?.let { content ->
                        Divider()
                        Text(text = "Summary: ${content.summary}")
                        Text(text = "Vocabulary: ${content.vocabulary.size}")
                        Text(text = "Exercises: ${content.practiceExercises.size}")
                        Text(text = "Grammar tables: ${content.grammarTables.size}")
                        Text(text = "Flashcards: ${content.flashcards.size}")
                    } ?: Text(text = "No content available yet.")
                }
            }
        }
    }
} 