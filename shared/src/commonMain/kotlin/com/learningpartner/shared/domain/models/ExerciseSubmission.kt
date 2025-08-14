package com.learningpartner.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseSubmissionRequest(
    val answer: String
)

@Serializable
data class ExerciseSubmissionResponse(
    val correct: Boolean,
    val explanation: String? = null,
    @SerialName("submission_id") val submissionId: Int? = null
) 