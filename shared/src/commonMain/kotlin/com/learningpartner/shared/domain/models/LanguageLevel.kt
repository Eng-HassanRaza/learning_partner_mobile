package com.learningpartner.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class LanguageLevel(
    val id: Int,
    val levelCode: String,
    val levelName: String
) 