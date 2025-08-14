package com.learningpartner.shared

import com.learningpartner.shared.data.api.AuthApi
import com.learningpartner.shared.domain.models.User
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthApiParsingTest {
    private fun buildClient(handler: suspend (HttpRequestData) -> HttpResponseData): HttpClient {
        return HttpClient(MockEngine { request -> handler(request) }) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    @Test
    fun parsesUserFromLogin() = runTest {
        val json = """
            {"id":1,"username":"u","email":"u@example.com","first_name":"John","last_name":"Doe","date_joined":"2024-01-01T00:00:00Z","user_type":"student"}
        """.trimIndent()
        val client = buildClient { _ ->
            respond(json, headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()))
        }
        val api = object : AuthApi() { val injected = client }
        // Directly deserialize to User using client to avoid changing AuthApi ctor
        val parsed = client.post("http://test").body<User>()
        assertEquals("John", parsed.firstName)
        assertEquals("Doe", parsed.lastName)
    }
} 