package com.learningpartner.shared.domain.repositories

import com.learningpartner.shared.domain.models.User

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<User>
    suspend fun logout(): Result<String>
    suspend fun testConnection(): Result<String>
    suspend fun getCurrentUser(): User?
    suspend fun saveUser(user: User)
    suspend fun clearUser()
} 