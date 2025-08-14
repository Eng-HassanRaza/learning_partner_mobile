package com.learningpartner.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable(with = SessionContentSerializer::class)
data class SessionContent(
    val id: Int,
    val summary: String,
    val vocabulary: List<Vocabulary>,
    val grammarTables: List<GrammarTable>,
    val practiceExercises: List<Exercise>,
    val flashcards: List<Flashcard>,
    val speakingPractice: List<String>
) 