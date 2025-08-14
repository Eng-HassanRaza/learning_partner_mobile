package com.learningpartner.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.learningpartner.shared.domain.models.*

private enum class Step { Summary, Vocabulary, Grammar, Flashcards, Exercises, Speaking, Final }

@Composable
fun SessionSteps(title: String, content: SessionContent) {
    val steps = remember { Step.values().toList() }
    var currentStep by rememberSaveable { mutableStateOf(0) }
    val completed = rememberSaveable {
        mutableStateListOf<Boolean>().apply { repeat(steps.size) { add(false) } }
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            LinearProgressIndicator(
                progress = (completed.count { it }.toFloat() / steps.size),
                modifier = Modifier.weight(1f)
            )
            Text("${completed.count { it }}/${steps.size}")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            steps.forEachIndexed { index, _ ->
                val isActive = index == currentStep
                Surface(
                    tonalElevation = if (isActive) 4.dp else 0.dp,
                    shape = CircleShape
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (isActive) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("${index + 1}")
                    }
                }
            }
        }

        Divider()

        when (steps[currentStep]) {
            Step.Summary -> SummaryStep(
                summary = content.summary,
                onCompleted = { completed[0] = true }
            )
            Step.Vocabulary -> VocabularyStep(
                items = content.vocabulary,
                onCompleted = { completed[1] = true }
            )
            Step.Grammar -> GrammarStep(
                tables = content.grammarTables,
                onCompleted = { completed[2] = true }
            )
            Step.Flashcards -> FlashcardsStep(
                cards = content.flashcards,
                onCompleted = { completed[3] = true }
            )
            Step.Exercises -> ExercisesStep(
                exercises = content.practiceExercises,
                onCompleted = { completed[4] = true }
            )
            Step.Speaking -> SpeakingStep(
                lines = content.speakingPractice,
                onCompleted = { completed[5] = true }
            )
            Step.Final -> FinalQuizStep(
                content = content,
                onCompleted = { completed[6] = true }
            )
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = { if (currentStep > 0) currentStep-- },
                enabled = currentStep > 0
            ) { Text("Back") }
            Button(
                onClick = { if (currentStep < steps.lastIndex) currentStep++ },
                enabled = completed[currentStep]
            ) { Text(if (currentStep == steps.lastIndex) "Finish" else "Next") }
        }
    }
}

@Composable
private fun SummaryStep(summary: String, onCompleted: () -> Unit) {
    var paraphrase by rememberSaveable { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Summary", style = MaterialTheme.typography.titleMedium)
        Text(summary)
        OutlinedTextField(value = paraphrase, onValueChange = { paraphrase = it }, label = { Text("Write a one-sentence paraphrase") })
        val done = paraphrase.trim().length >= 20
        Button(onClick = onCompleted, enabled = done) { Text("Mark Summary as Done") }
    }
}

@Composable
private fun VocabularyStep(items: List<Vocabulary>, onCompleted: () -> Unit) {
    var correct by rememberSaveable { mutableStateOf(0) }
    val target = minOf(5, items.size)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Vocabulary Trainer", style = MaterialTheme.typography.titleMedium)
        Text("Get $target correct to continue. Progress: $correct/$target")
        Button(onClick = { if (correct < target) correct++ ; if (correct >= target) onCompleted() }) { Text("I answered one correctly") }
    }
}

@Composable
private fun GrammarStep(tables: List<GrammarTable>, onCompleted: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Grammar", style = MaterialTheme.typography.titleMedium)
        Text("Read the rules and examples.")
        Button(onClick = onCompleted) { Text("I practiced grammar") }
    }
}

@Composable
private fun FlashcardsStep(cards: List<Flashcard>, onCompleted: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Flashcards", style = MaterialTheme.typography.titleMedium)
        Button(onClick = onCompleted) { Text("I reviewed flashcards") }
    }
}

@Composable
private fun ExercisesStep(exercises: List<Exercise>, onCompleted: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Exercises", style = MaterialTheme.typography.titleMedium)
        Button(onClick = onCompleted) { Text("I completed exercises") }
    }
}

@Composable
private fun SpeakingStep(lines: List<String>, onCompleted: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Speaking Practice", style = MaterialTheme.typography.titleMedium)
        Button(onClick = onCompleted) { Text("I practiced speaking") }
    }
}

@Composable
private fun FinalQuizStep(content: SessionContent, onCompleted: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Final Quiz", style = MaterialTheme.typography.titleMedium)
        Button(onClick = onCompleted) { Text("Finish Session") }
    }
} 