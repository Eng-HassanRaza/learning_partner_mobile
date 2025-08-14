package com.learningpartner.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponse<T>(
	val count: Int,
	val next: String? = null,
	val previous: String? = null,
	val results: List<T>
) 