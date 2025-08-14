package com.learningpartner.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    val completed = remember {
        mutableStateListOf<Boolean>().apply { repeat(steps.size) { add(false) } }
    }

    // Guard against out-of-bounds step index
    LaunchedEffect(steps.size) {
        if (currentStep !in steps.indices) currentStep = 0
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val fraction = (completed.count { it }.toFloat() / steps.size.coerceAtLeast(1)).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = fraction,
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
                enabled = completed.getOrNull(currentStep) == true
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
    var viewed by rememberSaveable { mutableStateOf(0) }
    val target = minOf(5, items.size.coerceAtLeast(1))
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Vocabulary", style = MaterialTheme.typography.titleMedium)
        Text("Browse items: $viewed/${items.size}. Review at least $target to continue.")
        Divider()
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f, true)) {
            items(items, key = { it.id }) { v ->
                Card { 
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = v.word, style = MaterialTheme.typography.titleMedium)
                        Text(text = v.translation, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                        Text(text = "Part of speech: ${v.partOfSpeech}", style = MaterialTheme.typography.bodySmall)
                        v.example?.let { Text(text = "Example: $it", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                    }
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { viewed = minOf(items.size, viewed + 3) }) { Text("Mark a few as reviewed") }
            Button(onClick = onCompleted, enabled = viewed >= target) { Text("Continue") }
        }
    }
}

@Composable
private fun GrammarStep(tables: List<GrammarTable>, onCompleted: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Grammar", style = MaterialTheme.typography.titleMedium)
        Divider()
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f, true)) {
            items(tables, key = { it.id }) { g ->
                Card {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(g.title, style = MaterialTheme.typography.titleMedium)
                        g.description?.let { Text(it, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                        if (g.rules.isNotEmpty()) {
                            Text("Rules:", style = MaterialTheme.typography.labelLarge)
                            g.rules.forEach { r ->
                                Text("• ${r.rule}" + (r.explanation?.let { ": $it" } ?: ""), style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        if (g.examples.isNotEmpty()) {
                            Text("Examples:", style = MaterialTheme.typography.labelLarge)
                            g.examples.forEach { ex -> Text("- $ex", style = MaterialTheme.typography.bodySmall) }
                        }
                    }
                }
            }
        }
        Button(onClick = onCompleted, enabled = tables.isNotEmpty()) { Text("Continue") }
    }
}

@Composable
private fun FlashcardsStep(cards: List<Flashcard>, onCompleted: () -> Unit) {
    var index by rememberSaveable { mutableStateOf(0) }
    var flipped by rememberSaveable { mutableStateOf(false) }
    val hasCards = cards.isNotEmpty()
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Flashcards", style = MaterialTheme.typography.titleMedium)
        if (!hasCards) {
            Text("No flashcards")
        } else {
            val card = cards[index]
            Card {
                Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(if (!flipped) card.front else card.back, style = MaterialTheme.typography.headlineSmall)
                    Text("(${index + 1}/${cards.size})", style = MaterialTheme.typography.bodySmall)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { flipped = !flipped }) { Text(if (!flipped) "Flip" else "Hide") }
                OutlinedButton(onClick = { if (index > 0) { index--; flipped = false } }, enabled = index > 0) { Text("Prev") }
                Button(onClick = { if (index < cards.lastIndex) { index++; flipped = false } else onCompleted() }, enabled = true) { Text(if (index == cards.lastIndex) "Continue" else "Next") }
            }
        }
    }
}

@Composable
private fun ExercisesStep(exercises: List<Exercise>, onCompleted: () -> Unit) {
    var index by rememberSaveable { mutableStateOf(0) }
    var answer by rememberSaveable { mutableStateOf("") }
    var selectedOption by rememberSaveable { mutableStateOf<String?>(null) }
    var feedback by rememberSaveable { mutableStateOf<String?>(null) }
    val hasExercises = exercises.isNotEmpty()

    fun checkAndAdvance() {
        val e = exercises[index]
        val userAnswer = if (e.options != null && e.type == "multiple_choice") selectedOption ?: "" else answer
        val correct = userAnswer.trim().equals(e.correctAnswer.trim(), ignoreCase = true)
        feedback = if (correct) "Correct!" else "Incorrect. ${e.explanation ?: ""}"
        if (correct) {
            if (index < exercises.lastIndex) {
                index++
                answer = ""
                selectedOption = null
                feedback = null
            } else {
                onCompleted()
            }
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Exercises", style = MaterialTheme.typography.titleMedium)
        if (!hasExercises) {
            Text("No exercises available")
        } else {
            val e = exercises[index]
            Card {
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Q${index + 1}: ${e.question}")
                    when (e.type) {
                        "multiple_choice" -> {
                            e.options.orEmpty().forEach { opt ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(selected = selectedOption == opt, onClick = { selectedOption = opt })
                                    Spacer(Modifier.width(8.dp))
                                    Text(opt)
                                }
                            }
                        }
                        else -> {
                            OutlinedTextField(value = answer, onValueChange = { answer = it }, label = { Text("Your answer") })
                        }
                    }
                    feedback?.let { Text(it, color = MaterialTheme.colorScheme.secondary) }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = { if (index > 0) { index--; feedback = null; answer = ""; selectedOption = null } }, enabled = index > 0) { Text("Back") }
                        Button(onClick = { checkAndAdvance() }) { Text(if (index == exercises.lastIndex) "Submit" else "Check") }
                    }
                }
            }
        }
    }
}

@Composable
private fun SpeakingStep(lines: List<String>, onCompleted: () -> Unit) {
    val practiced = remember { mutableStateListOf<Boolean>().apply { repeat(lines.size) { add(false) } } }
    val completedCount = practiced.count { it }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Speaking Practice", style = MaterialTheme.typography.titleMedium)
        Text("Mark each line after you practice it. $completedCount/${lines.size}")
        Divider()
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f, true)) {
            items(lines.indices.toList(), key = { it }) { i ->
                Card {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = practiced[i], onCheckedChange = { practiced[i] = it })
                        Spacer(Modifier.width(8.dp))
                        Text(lines[i])
                    }
                }
            }
        }
        Button(onClick = onCompleted, enabled = lines.isEmpty() || practiced.all { it }) { Text("Continue") }
    }
}

@Composable
private fun FinalQuizStep(content: SessionContent, onCompleted: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Final Quiz", style = MaterialTheme.typography.titleMedium)
        Text("You can proceed for now. We'll connect to the quiz endpoint next.")
        Button(onClick = onCompleted) { Text("Finish Session") }
    }
} 