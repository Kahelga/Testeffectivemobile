package com.example.testEffectiveMobile.shared.courses.data.network

import com.example.testEffectiveMobile.shared.courses.data.model.CoursesModel
import retrofit2.http.GET

interface CoursesApi {
    @GET("courses")
    suspend fun getAll(): CoursesModel
}