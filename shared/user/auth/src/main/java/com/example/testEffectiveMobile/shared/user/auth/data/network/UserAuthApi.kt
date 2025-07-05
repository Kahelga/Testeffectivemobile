package com.example.testEffectiveMobile.shared.user.auth.data.network

import com.example.testEffectiveMobile.shared.user.auth.data.model.AuthCredentials
import com.example.testEffectiveMobile.shared.user.auth.data.model.AuthResult
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAuthApi {
    @POST("auth/login")
    suspend  fun signIn(@Body request: AuthCredentials): AuthResult
}