package com.example.testEffectiveMobile.shared.user.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthCredentials(
    val email: String,
    val password: String
)
