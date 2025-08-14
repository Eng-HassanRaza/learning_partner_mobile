package com.learningpartner.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.learningpartner.android.ui.viewmodels.AuthViewModel
import com.learningpartner.android.ui.viewmodels.StudentViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val authViewModel: AuthViewModel = koinViewModel()
    val studentViewModel: StudentViewModel = koinViewModel()
    
    var selectedTab by remember { mutableStateOf(0) }
    
    LaunchedEffect(Unit) {
        studentViewModel.loadDashboard()
        studentViewModel.loadClasses()
        studentViewModel.loadSessions()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Learning Partner") },
                actions = {
                    IconButton(onClick = { authViewModel.logout() }) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                    label = { Text("Dashboard") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Class, contentDescription = "Classes") },
                    label = { Text("Classes") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.VideoLibrary, contentDescription = "Sessions") },
                    label = { Text("Sessions") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Assessment, contentDescription = "Progress") },
                    label = { Text("Progress") },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> DashboardScreen()
                1 -> ClassesScreen()
                2 -> SessionsScreen()
                3 -> ProgressScreen()
            }
        }
    }
} 