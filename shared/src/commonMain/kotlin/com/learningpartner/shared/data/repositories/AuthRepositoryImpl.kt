package com.learningpartner.shared.data.repositories

import com.learningpartner.shared.data.api.AuthApi
import com.learningpartner.shared.domain.models.User
import com.learningpartner.shared.domain.repositories.AuthRepository
import com.russhwolf.multiplatformsettings.MultiplatformSettings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val settings: MultiplatformSettings
) : AuthRepository {
    
    private val json = Json { ignoreUnknownKeys = true }
    
    override suspend fun login(username: String, password: String): Result<User> {
        return try {
            val user = authApi.login(username, password)
            saveUser(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun logout(): Result<String> {
        return try {
            val response = authApi.logout()
            clearUser()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getCurrentUser(): User? {
        val userJson = settings.getString("current_user", null)
        return userJson?.let { json.decodeFromString<User>(it) }
    }
    
    override suspend fun saveUser(user: User) {
        val userJson = json.encodeToString(user)
        settings.putString("current_user", userJson)
    }
    
    override suspend fun clearUser() {
        settings.remove("current_user")
    }
} 