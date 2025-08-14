package com.learningpartner.shared.domain.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class User(
    val id: Int,
    val username: String,
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("date_joined")
    val dateJoined: String? = null,
    @SerialName("user_type")
    val userType: String? = null
) {
    val fullName: String
        get() = "$firstName $lastName"
} 