package com.example.testEffectiveMobile.feature.auth.presentation

import com.example.testEffectiveMobile.shared.user.auth.domain.DTO.AuthResponse

interface AuthState {
    data object Initial : AuthState
    data class Failure(val message: String?) : AuthState
    data class Success(val response: AuthResponse) : AuthState
    data object LoggedOut : AuthState
}