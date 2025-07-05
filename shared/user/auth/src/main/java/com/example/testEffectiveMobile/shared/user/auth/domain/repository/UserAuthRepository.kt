package com.example.testEffectiveMobile.shared.user.auth.domain.repository

import com.example.testEffectiveMobile.shared.user.auth.domain.DTO.AuthResponse

interface UserAuthRepository {
    suspend fun signIn(email: String, pass: String): AuthResponse
}