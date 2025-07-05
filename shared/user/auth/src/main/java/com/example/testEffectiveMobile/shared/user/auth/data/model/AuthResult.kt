package com.example.testEffectiveMobile.shared.user.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthResult(
    val success: Boolean,
    val reason: String?
)
