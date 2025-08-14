package com.learningpartner.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable(with = ExerciseSerializer::class)
data class Exercise(
    val id: Int,
    val type: String, // "fill_blank", "translation", "writing", "multiple_choice"
    val question: String,
    val correctAnswer: String,
    val options: List<String>? = null,
    val explanation: String? = null,
    val difficulty: String? = null
) 