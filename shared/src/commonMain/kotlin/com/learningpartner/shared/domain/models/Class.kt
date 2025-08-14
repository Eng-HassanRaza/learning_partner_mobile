package com.learningpartner.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Class(
    val id: Int,
    val name: String,
    val teacher: LanguageTeacher,
    val level: LanguageLevel,
    val classType: String,
    val maxStudents: Int,
    val currentStudents: Int,
    val description: String? = null,
    val startDate: String? = null,
    val endDate: String? = null
) 