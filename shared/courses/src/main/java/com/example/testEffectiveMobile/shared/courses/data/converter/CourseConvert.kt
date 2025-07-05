package com.example.testEffectiveMobile.shared.courses.data.converter

import com.example.testEffectiveMobile.shared.courses.data.model.CourseModel
import com.example.testEffectiveMobile.shared.courses.data.model.CoursesModel
import com.example.testEffectiveMobile.shared.courses.domain.entity.Course
import com.example.testEffectiveMobile.shared.courses.domain.entity.Courses

class CourseConvert {
    private fun courseConvert(model:CourseModel):Course{
        return Course(
            id = model.id,
            title = model.title,
            text = model.text,
            price = model.price,
            rate = model.rate,
            startDate = model.startDate,
            hasLike = model.hasLike,
            publishDate = model.publishDate
        )
    }
    fun convert(model: CoursesModel): Courses {
        return Courses(
            courses = model.courses.map { courseConvert(it)}
        )
    }
}