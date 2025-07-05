package com.example.testEffectiveMobile.feature.courses.presentation

import com.example.testEffectiveMobile.shared.courses.domain.entity.Courses

interface HomeState {
    data object Initial: HomeState
    data object Loading:HomeState
    data class Failure(val message: String?) :HomeState
    data class Content(val courses: Courses):HomeState
}