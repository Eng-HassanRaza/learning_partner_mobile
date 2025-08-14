package com.learningpartner.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class GrammarTable(
    val id: Int,
    val title: String,
    val description: String,
    val rules: List<GrammarRule>,
    val examples: List<String>
)

@Serializable
data class GrammarRule(
    val id: Int,
    val rule: String,
    val explanation: String
) 