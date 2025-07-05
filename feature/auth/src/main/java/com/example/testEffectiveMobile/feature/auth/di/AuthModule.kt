package com.example.testEffectiveMobile.feature.auth.di

import com.example.testEffectiveMobile.feature.auth.presentation.AuthViewModel
import com.example.testEffectiveMobile.shared.user.auth.data.converter.AuthConvert
import com.example.testEffectiveMobile.shared.user.auth.data.network.UserAuthApi
import com.example.testEffectiveMobile.shared.user.auth.data.repository.UserAuthRepositoryImpl
import com.example.testEffectiveMobile.shared.user.auth.domain.repository.UserAuthRepository
import com.example.testEffectiveMobile.shared.user.auth.domain.usecase.AuthUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    single { get<Retrofit>().create(UserAuthApi::class.java) }
    singleOf(::AuthConvert)
    singleOf(::UserAuthRepositoryImpl) bind UserAuthRepository::class
    factoryOf(::AuthUseCase)
    viewModelOf(::AuthViewModel)
}