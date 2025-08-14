package com.learningpartner.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlashcardReviewRequest(
    val quality: Int,
    @SerialName("time_taken_ms") val timeTakenMs: Long
)

@Serializable
data class FlashcardReviewResponse(
    @SerialName("next_due") val nextDue: String? = null,
    @SerialName("review_id") val reviewId: Int? = null
) 