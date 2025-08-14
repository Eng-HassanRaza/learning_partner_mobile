package com.learningpartner.shared.data.api

import com.learningpartner.shared.domain.models.User
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class AuthApi {
    private val client = ApiClient.client
    private val baseUrl = ApiClient.getBaseUrl()
    
    suspend fun login(username: String, password: String): User {
        val response = client.post("${baseUrl}auth/") {
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "username" to username,
                    "password" to password
                )
            )
        }
        
        return client.body(response.bodyAsText())
    }
    
    suspend fun logout(): String {
        val response = client.delete("${baseUrl}auth/")
        return response.bodyAsText()
    }
} 