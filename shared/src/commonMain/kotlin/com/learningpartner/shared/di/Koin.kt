package com.learningpartner.shared.di

import com.learningpartner.shared.data.api.AuthApi
import com.learningpartner.shared.data.api.StudentApi
import com.learningpartner.shared.data.repositories.AuthRepositoryImpl
import com.learningpartner.shared.data.repositories.StudentRepositoryImpl
import com.learningpartner.shared.domain.repositories.AuthRepository
import com.learningpartner.shared.domain.repositories.StudentRepository
import com.russhwolf.multiplatformsettings.MultiplatformSettings
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(sharedModule)
    }

val sharedModule = module {
    // Settings
    single<MultiplatformSettings> { MultiplatformSettings() }
    
    // API
    single { AuthApi() }
    single { StudentApi() }
    
    // Repositories
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<StudentRepository> { StudentRepositoryImpl(get()) }
} 