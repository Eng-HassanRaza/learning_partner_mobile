package com.learningpartner.shared.data.api

import com.learningpartner.shared.domain.models.User
import io.ktor.client.request.*
import io.ktor.client.call.*
import io.ktor.http.*
import io.ktor.client.plugins.logging.*

class AuthApi {
    private val client = ApiClient.client
    private val baseUrl = ApiClient.getBaseUrl()
    
    suspend fun login(username: String, password: String): User {
        println("🔐 Attempting login to: ${baseUrl}auth/")
        println("📧 Username: $username")
        
        return try {
            // First, let's test if the base URL is reachable
            println("🌐 Testing connectivity to base URL...")
            try {
                val testResponse = client.get(baseUrl)
                println("✅ Base URL is reachable, status: ${testResponse.status}")
            } catch (e: Exception) {
                println("⚠️ Base URL connectivity test failed: ${e.message}")
            }
            
            val response = client.post("${baseUrl}auth/") {
                contentType(ContentType.Application.Json)
                setBody(
                    mapOf(
                        "username" to username,
                        "password" to password
                    )
                )
            }
            println("✅ Login successful")
            response.body()
        } catch (e: Exception) {
            println("❌ Login failed: ${e.message}")
            println("🔍 Exception type: ${e::class.simpleName}")
            e.printStackTrace()
            throw e
        }
    }
    
    suspend fun logout(): String {
        return try {
            val response = client.delete("${baseUrl}auth/")
            println("✅ Logout successful")
            response.body()
        } catch (e: Exception) {
            println("❌ Logout failed: ${e.message}")
            throw e
        }
    }
    
    suspend fun testConnection(): String {
        return try {
            println("🌐 Testing connection to: $baseUrl")
            val response = client.get(baseUrl)
            println("✅ Connection test successful, status: ${response.status}")
            "Connection successful - Status: ${response.status}"
        } catch (e: Exception) {
            println("❌ Connection test failed: ${e.message}")
            throw e
        }
    }
} 