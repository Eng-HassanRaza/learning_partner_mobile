package com.learningpartner.shared.data.api

import com.russhwolf.settings.Settings
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
    private const val DEFAULT_BASE_URL = "https://d24ddb36a524.ngrok-free.app/api/v1/"
    private const val SETTINGS_BASE_URL_KEY = "api_base_url"

    // Settings will be provided via DI; but for KMM common, we access a lazy singleton
    private val settings: Settings by lazy { com.russhwolf.settings.Settings() }

    private val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    val client = HttpClient {
        install(HttpCookies) {
            storage = SettingsCookiesStorage(settings, json)
        }
        install(ContentNegotiation) {
            json(json)
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
                throw cause
            }
        }
    }

    fun getBaseUrl(): String = settings.getStringOrNull(SETTINGS_BASE_URL_KEY) ?: DEFAULT_BASE_URL
    fun setBaseUrl(url: String) { settings.putString(SETTINGS_BASE_URL_KEY, url) }
} 