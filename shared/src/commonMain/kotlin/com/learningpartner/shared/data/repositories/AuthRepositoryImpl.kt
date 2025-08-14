package com.learningpartner.shared.data.repositories

import com.learningpartner.shared.data.api.AuthApi
import com.learningpartner.shared.domain.models.User
import com.learningpartner.shared.domain.repositories.AuthRepository
import com.russhwolf.settings.Settings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val settings: Settings
) : AuthRepository {
    
    private val json = Json { ignoreUnknownKeys = true }
    
    override suspend fun login(username: String, password: String): Result<User> {
        return try {
            println("🔄 AuthRepository: Starting login process")
            val user = authApi.login(username, password)
            println("💾 AuthRepository: Saving user to local storage")
            saveUser(user)
            println("✅ AuthRepository: Login completed successfully")
            Result.success(user)
        } catch (e: Exception) {
            println("❌ AuthRepository: Login failed with exception: ${e.message}")
            e.printStackTrace()
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
    
    override suspend fun testConnection(): Result<String> {
        return try {
            println("🔗 AuthRepository: Testing connection...")
            val response = authApi.testConnection()
            println("✅ AuthRepository: Connection test successful")
            Result.success(response)
        } catch (e: Exception) {
            println("❌ AuthRepository: Connection test failed: ${e.message}")
            Result.failure(e)
        }
    }
    
    override suspend fun getCurrentUser(): User? {
        val userJson = settings.getStringOrNull("current_user")
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