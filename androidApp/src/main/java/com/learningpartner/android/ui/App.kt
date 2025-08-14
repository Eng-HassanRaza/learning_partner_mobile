package com.learningpartner.android.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.learningpartner.android.ui.screens.LoginScreen
import com.learningpartner.android.ui.screens.MainScreen
import com.learningpartner.android.ui.viewmodels.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun App() {
    val authViewModel: AuthViewModel = koinViewModel()
    
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when {
                authViewModel.isLoggedIn.value -> {
                    MainScreen()
                }
                else -> {
                    LoginScreen()
                }
            }
        }
    }
} 