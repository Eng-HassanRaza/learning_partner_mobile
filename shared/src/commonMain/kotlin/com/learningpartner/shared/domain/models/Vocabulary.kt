package com.learningpartner.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable(with = VocabularySerializer::class)
data class Vocabulary(
    val id: Int,
    val word: String,
    val translation: String,
    val partOfSpeech: String,
    val example: String? = null,
    val difficulty: String? = null
) 