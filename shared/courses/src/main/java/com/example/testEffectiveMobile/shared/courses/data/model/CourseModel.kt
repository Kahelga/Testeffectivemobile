package com.example.testEffectiveMobile.shared.courses.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CourseModel(
    val id:Int,
    val title:String,
    val text:String,
    val price:String,
    val rate:String,
    val startDate:String,
    val hasLike:Boolean,
    val publishDate:String
)
