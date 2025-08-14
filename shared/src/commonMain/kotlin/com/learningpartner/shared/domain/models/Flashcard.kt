package com.learningpartner.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable(with = FlashcardSerializer::class)
data class Flashcard(
    val id: Int,
    val front: String,
    val back: String,
    val category: String? = null,
    val difficulty: String? = null
) 