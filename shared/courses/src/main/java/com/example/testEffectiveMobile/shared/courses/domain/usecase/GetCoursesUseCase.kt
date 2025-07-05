package com.example.testEffectiveMobile.shared.courses.domain.usecase

import com.example.testEffectiveMobile.shared.courses.domain.entity.Courses
import com.example.testEffectiveMobile.shared.courses.domain.repository.CourseRepository

class GetCoursesUseCase(private val repository: CourseRepository) {
    suspend operator fun invoke(): Courses =repository.getAll()
}