package com.example.testEffectiveMobile.shared.user.auth.data.repository

import com.example.testEffectiveMobile.shared.user.auth.data.converter.AuthConvert
import com.example.testEffectiveMobile.shared.user.auth.data.model.AuthCredentials
import com.example.testEffectiveMobile.shared.user.auth.data.network.UserAuthApi
import com.example.testEffectiveMobile.shared.user.auth.domain.DTO.AuthResponse
import com.example.testEffectiveMobile.shared.user.auth.domain.repository.UserAuthRepository

class UserAuthRepositoryImpl(
    private val userAuthApi: UserAuthApi,
    private val authConvert: AuthConvert
) : UserAuthRepository {

    override suspend fun signIn(login: String, pass: String): AuthResponse {
        val request = AuthCredentials(login, pass)
        val response = userAuthApi.signIn(request)
        return authConvert.convert(response)
    }
}