package com.learningpartner.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpeakingAttemptRequest(
    val line: String,
    @SerialName("self_rating") val selfRating: Int,
    @SerialName("duration_ms") val durationMs: Long? = null
)

@Serializable
data class SpeakingAttemptResponse(
    @SerialName("attempt_id") val attemptId: Int,
    @SerialName("count_for_session") val countForSession: Int,
    @SerialName("last_attempt_at") val lastAttemptAt: String
) 