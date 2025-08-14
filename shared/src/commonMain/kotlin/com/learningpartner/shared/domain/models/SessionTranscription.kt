package com.learningpartner.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionTranscription(
    val id: Int,
    @SerialName("class_session")
    val classSession: Class,
    @SerialName("processing_status")
    val processingStatus: String, // "completed", "processing", "failed"
    @SerialName("video_duration")
    val videoDuration: Int,
    @SerialName("language_detected")
    val languageDetected: String,
    val content: SessionContent? = null,
    val gating: GatingConfig? = null
) 