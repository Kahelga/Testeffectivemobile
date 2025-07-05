package com.example.testEffectiveMobile.shared.user.auth.domain.usecase

import com.example.testEffectiveMobile.shared.user.auth.domain.DTO.AuthResponse
import com.example.testEffectiveMobile.shared.user.auth.domain.repository.UserAuthRepository

class AuthUseCase(private val userAuthRepository: UserAuthRepository) {
    suspend operator fun invoke(email: String, pass: String): AuthResponse =
        userAuthRepository.signIn(email, pass)
}