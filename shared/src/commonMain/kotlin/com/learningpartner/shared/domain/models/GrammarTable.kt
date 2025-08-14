package com.learningpartner.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable(with = GrammarTableSerializer::class)
data class GrammarTable(
    val id: Int,
    val title: String,
    val description: String? = null,
    val rules: List<GrammarRule>,
    val examples: List<String>
)

@Serializable(with = GrammarRuleSerializer::class)
data class GrammarRule(
    val id: Int,
    val rule: String,
    val explanation: String? = null
) 