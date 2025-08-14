package com.learningpartner.android.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learningpartner.shared.domain.models.User
import com.learningpartner.shared.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    init {
        checkAuthState()
    }
    
    private fun checkAuthState() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            _currentUser.value = user
            _isLoggedIn.value = user != null
        }
    }
    
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            authRepository.login(username, password)
                .onSuccess { user ->
                    _currentUser.value = user
                    _isLoggedIn.value = true
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Login failed"
                }
            
            _isLoading.value = false
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            _isLoading.value = true
            
            authRepository.logout()
                .onSuccess {
                    _currentUser.value = null
                    _isLoggedIn.value = false
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Logout failed"
                }
            
            _isLoading.value = false
        }
    }
    
    fun clearError() {
        _error.value = null
    }
} 