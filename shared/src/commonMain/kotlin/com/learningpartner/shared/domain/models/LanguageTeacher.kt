package com.learningpartner.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class LanguageTeacher(
    val id: Int,
    val user: User,
    val bio: String? = null,
    val specialization: String? = null
) 