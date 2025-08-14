package com.learningpartner.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GatingConfig(
    val summary: SummaryGate? = null,
    val vocabulary: VocabularyGate? = null,
    val grammar: GrammarGate? = null,
    val flashcards: FlashcardsGate? = null,
    val exercises: ExercisesGate? = null,
    val speaking: SpeakingGate? = null
)

@Serializable
data class SummaryGate(
    @SerialName("min_tasks") val minTasks: Int = 1
)

@Serializable
data class VocabularyGate(
    @SerialName("target_correct") val targetCorrect: Int = 5
)

@Serializable
data class GrammarGate(
    @SerialName("min_tasks") val minTasks: Int = 1
)

@Serializable
data class FlashcardsGate(
    @SerialName("min_reviews") val minReviews: Int = 8
)

@Serializable
data class ExercisesGate(
    @SerialName("min_correct") val minCorrect: Int = 3
)

@Serializable
data class SpeakingGate(
    @SerialName("min_attempts") val minAttempts: Int = 3
) 