package com.learningpartner.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val id: Int,
    val user: User,
    @SerialName("current_level")
    val currentLevel: LanguageLevel,
    @SerialName("progress_percentage")
    val progressPercentage: Int,
    @SerialName("enrollment_date")
    val enrollmentDate: String? = null
) 