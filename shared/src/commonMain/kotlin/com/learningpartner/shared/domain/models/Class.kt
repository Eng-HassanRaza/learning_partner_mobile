package com.learningpartner.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Class(
    val id: Int,
    val name: String,
    val teacher: LanguageTeacher,
    val level: LanguageLevel,
    @SerialName("class_type")
    val classType: String,
    @SerialName("max_students")
    val maxStudents: Int,
    @SerialName("current_students")
    val currentStudents: Int,
    val description: String? = null,
    @SerialName("start_date")
    val startDate: String? = null,
    @SerialName("end_date")
    val endDate: String? = null
) 