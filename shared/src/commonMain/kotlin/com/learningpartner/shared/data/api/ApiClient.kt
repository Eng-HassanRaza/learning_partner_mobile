package com.learningpartner.shared.data.api

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.defaultRequest.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ApiClient {
    private const val BASE_URL = "https://cb64f97bbe57.ngrok-free.app/api/v1/"
    
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        
        // Add default headers for ngrok
        install(DefaultRequest) {
            header(HttpHeaders.UserAgent, "LearningPartner-Mobile")
        }
    }
    
    fun getBaseUrl(): String = BASE_URL
} 