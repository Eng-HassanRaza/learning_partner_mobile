package com.learningpartner.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionProgress(
    val id: Int? = null,
    @SerialName("session_id") val sessionId: Int,
    val steps: List<StepProgress> = emptyList(),
    @SerialName("overall_status") val overallStatus: String = "not_started",
    @SerialName("total_score") val totalScore: Int = 0,
    @SerialName("total_duration_sec") val totalDurationSec: Int = 0,
    @SerialName("updated_at") val updatedAt: String? = null
)

@Serializable
data class StepProgress(
    val step: String,
    val status: String, // not_started, in_progress, completed
    val score: Int? = null,
    val attempts: Int? = null,
    @SerialName("duration_sec") val durationSec: Int? = null,
    val correct: Int? = null,
    val target: Int? = null
)

@Serializable
data class SessionProgressUpdate(
    val steps: List<StepProgress>,
    @SerialName("overall_status") val overallStatus: String,
    @SerialName("total_score") val totalScore: Int? = null,
    @SerialName("total_duration_sec") val totalDurationSec: Int? = null
) 