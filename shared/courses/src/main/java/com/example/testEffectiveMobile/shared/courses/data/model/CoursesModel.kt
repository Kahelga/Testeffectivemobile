package com.example.testEffectiveMobile.shared.courses.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CoursesModel(
    val courses:List<CourseModel>
)
