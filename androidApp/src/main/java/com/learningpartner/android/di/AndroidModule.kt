package com.learningpartner.android.di

import com.learningpartner.android.ui.viewmodels.AuthViewModel
import com.learningpartner.android.ui.viewmodels.StudentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { StudentViewModel(get()) }
} 