package com.learningpartner.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class SessionTranscription(
    val id: Int,
    val classSession: Class,
    val processingStatus: String, // "completed", "processing", "failed"
    val videoDuration: Int,
    val languageDetected: String,
    val content: SessionContent? = null
) 