package com.learningpartner.shared.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LanguageLevel(
    val id: Int,
    @SerialName("level_code")
    val levelCode: String,
    @SerialName("level_name")
    val levelName: String
) 