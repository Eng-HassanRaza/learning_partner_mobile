package com.learningpartner.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizResponse(
    val quiz: List<Exercise>,
    val count: Int
)

@Serializable
data class QuizSubmitRequest(
    val answers: Map<String, String>
)

@Serializable
data class QuizSubmitResponse(
    @SerialName("submission_id") val submissionId: Int,
    val score: Int,
    val correct: Int,
    val total: Int
)

@Serializable
data class CompleteSessionRequest(
    @SerialName("final_score") val finalScore: Int? = null
)

@Serializable
data class CompleteSessionResponse(
    @SerialName("progress_id") val progressId: Int,
    @SerialName("overall_status") val overallStatus: String,
    @SerialName("total_score") val totalScore: Int,
    @SerialName("xp_awarded") val xpAwarded: Int,
    @SerialName("streak_after") val streakAfter: Int
) 