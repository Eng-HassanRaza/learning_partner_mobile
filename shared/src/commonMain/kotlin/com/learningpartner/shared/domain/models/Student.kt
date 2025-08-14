package com.learningpartner.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val id: Int,
    val user: User,
    val currentLevel: LanguageLevel,
    val progressPercentage: Int,
    val enrollmentDate: String? = null
) 