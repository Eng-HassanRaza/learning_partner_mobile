package com.learningpartner.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val dateJoined: String? = null,
    val userType: String? = null
) {
    val fullName: String
        get() = "$firstName $lastName"
} 