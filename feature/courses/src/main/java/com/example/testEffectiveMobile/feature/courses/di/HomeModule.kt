package com.example.testEffectiveMobile.feature.courses.di

import com.example.testEffectiveMobile.feature.courses.presentation.HomeViewModel
import com.example.testEffectiveMobile.shared.courses.data.converter.CourseConvert
import com.example.testEffectiveMobile.shared.courses.data.network.CoursesApi
import com.example.testEffectiveMobile.shared.courses.data.repository.CourseRepositoryImpl
import com.example.testEffectiveMobile.shared.courses.domain.repository.CourseRepository
import com.example.testEffectiveMobile.shared.courses.domain.usecase.GetCoursesUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val homeModule = module {
    single { get<Retrofit>().create(CoursesApi::class.java) }
    singleOf(::CourseConvert)
    singleOf(::CourseRepositoryImpl) bind CourseRepository::class
    factoryOf(::GetCoursesUseCase)
    viewModelOf(::HomeViewModel)
}