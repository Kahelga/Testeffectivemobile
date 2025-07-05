package com.example.testEffectiveMobile.shared.user.auth.data.converter

import com.example.testEffectiveMobile.shared.user.auth.data.model.AuthResult
import com.example.testEffectiveMobile.shared.user.auth.domain.DTO.AuthResponse

class AuthConvert {
    fun convert(model:AuthResult): AuthResponse {
        return AuthResponse(
            success = model.success,
            reason = model.reason
        )
    }
}