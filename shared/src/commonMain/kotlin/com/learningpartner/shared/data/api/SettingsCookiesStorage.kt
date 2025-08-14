package com.learningpartner.shared.data.api

import com.russhwolf.settings.Settings
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.http.Cookie
import io.ktor.http.Url
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Simple persistent cookie storage based on Settings.
 * Stores cookies per host. Expired cookies are filtered out on read.
 */
class SettingsCookiesStorage(
    private val settings: Settings,
    private val json: Json = Json { ignoreUnknownKeys = true }
) : CookiesStorage {

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
        val key = keyForHost(requestUrl.host)
        val existing = load(key).toMutableList()
        // Replace cookie with same name and path
        val index = existing.indexOfFirst { it.name == cookie.name && it.path == cookie.path }
        val dto = cookie.toDto(requestUrl.host)
        if (index >= 0) existing[index] = dto else existing.add(dto)
        save(key, existing)
    }

    override suspend fun get(requestUrl: Url): List<Cookie> {
        val key = keyForHost(requestUrl.host)
        val now = System.currentTimeMillis()
        val cookies = load(key)
            .filter { it.expiresAtMs == null || it.expiresAtMs!! > now }
            .map { it.toCookie() }
        return cookies
    }

    override fun close() { /* no-op */ }

    private fun keyForHost(host: String) = "cookies_$host"

    private fun load(key: String): List<CookieDTO> {
        val raw = settings.getStringOrNull(key) ?: return emptyList()
        return runCatching { json.decodeFromString<List<CookieDTO>>(raw) }.getOrDefault(emptyList())
    }

    private fun save(key: String, cookies: List<CookieDTO>) {
        val raw = json.encodeToString(cookies)
        settings.putString(key, raw)
    }
}

@Serializable
private data class CookieDTO(
    val name: String,
    val value: String,
    val domain: String? = null,
    val path: String? = null,
    @SerialName("expires_at_ms") val expiresAtMs: Long? = null,
    val secure: Boolean = false,
    @SerialName("http_only") val httpOnly: Boolean = false
) {
    fun toCookie(): Cookie = Cookie(
        name = name,
        value = value,
        domain = domain,
        path = path ?: "/",
        expires = expiresAtMs?.let { io.ktor.util.date.GMTDate(it) },
        secure = secure,
        httpOnly = httpOnly
    )
}

private fun Cookie.toDto(defaultDomain: String): CookieDTO = CookieDTO(
    name = name,
    value = value,
    domain = domain ?: defaultDomain,
    path = path,
    expiresAtMs = expires?.timestamp,
    secure = secure,
    httpOnly = httpOnly
) 