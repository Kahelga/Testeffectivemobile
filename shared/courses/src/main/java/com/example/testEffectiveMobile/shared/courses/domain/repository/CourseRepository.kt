package com.example.testEffectiveMobile.shared.courses.domain.repository

import com.example.testEffectiveMobile.shared.courses.domain.entity.Courses

interface CourseRepository {
    suspend fun getAll():Courses
}