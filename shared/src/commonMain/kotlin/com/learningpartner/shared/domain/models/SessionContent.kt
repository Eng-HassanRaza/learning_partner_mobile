package com.learningpartner.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionContent(
    val id: Int,
    val summary: String,
    val vocabulary: List<Vocabulary>,
    @SerialName("grammar_tables")
    val grammarTables: List<GrammarTable>,
    @SerialName("practice_exercises")
    val practiceExercises: List<Exercise>,
    val flashcards: List<Flashcard>,
    @SerialName("speaking_practice")
    val speakingPractice: List<String>
) 