package com.learningpartner.shared.data.api

import com.learningpartner.shared.domain.models.User
import io.ktor.client.request.*
import io.ktor.client.call.*
import io.ktor.http.*

class AuthApi {
    private val client = ApiClient.client
    private val baseUrl = ApiClient.getBaseUrl()
    
    suspend fun login(username: String, password: String): User {
        return client.post("${baseUrl}auth/") {
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "username" to username,
                    "password" to password
                )
            )
        }.body()
    }
    
    suspend fun logout(): String {
        return client.delete("${baseUrl}auth/").body()
    }
} 