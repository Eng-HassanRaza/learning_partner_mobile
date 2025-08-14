package com.learningpartner.shared.data.api

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ApiClient {
    private const val BASE_URL = "https://d24ddb36a524.ngrok-free.app/api/v1/"
    
    val client = HttpClient {
        install(HttpCookies) {
            storage = AcceptAllCookiesStorage()
        }
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
        HttpResponseValidator {
            validateResponse { response ->
                val status = response.status
                if (status.value >= 400) {
                    val body = runCatching { response.bodyAsText() }.getOrDefault("")
                    throw ResponseException(response, "HTTP ${'$'}status: ${'$'}body")
                }
            }
            handleResponseExceptionWithRequest { cause, _ ->
                // Let upstream handle
                throw cause
            }
        }
    }
    
    fun getBaseUrl(): String = BASE_URL
} 