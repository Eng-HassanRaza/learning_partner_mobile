package com.learningpartner.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Exercise(
    val id: Int,
    val type: String, // "fill_blank", "translation", "writing", "multiple_choice"
    val question: String,
    @SerialName("correct_answer")
    val correctAnswer: String,
    val options: List<String>? = null,
    val explanation: String? = null,
    val difficulty: String? = null
) 