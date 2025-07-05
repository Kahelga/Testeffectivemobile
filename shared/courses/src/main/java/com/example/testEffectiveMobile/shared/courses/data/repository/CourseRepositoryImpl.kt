package com.example.testEffectiveMobile.shared.courses.data.repository

import com.example.testEffectiveMobile.shared.courses.data.converter.CourseConvert
import com.example.testEffectiveMobile.shared.courses.data.network.CoursesApi
import com.example.testEffectiveMobile.shared.courses.domain.entity.Courses
import com.example.testEffectiveMobile.shared.courses.domain.repository.CourseRepository

class CourseRepositoryImpl(
    private val coursesApi: CoursesApi,
    private val courseConvert: CourseConvert
):CourseRepository {
    override suspend fun getAll(): Courses {
       val model=coursesApi.getAll()
        return courseConvert.convert(model)
    }

}