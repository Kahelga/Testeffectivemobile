package com.example.testEffectiveMobile.shared.courses.data.local

class FavoriteCourseRepository(private val dao: FavoriteCourseDao) {

    suspend fun getFavorites(): List<FavoriteCourse> = dao.getAllFavorites()

    suspend fun addFavorite(course: FavoriteCourse) = dao.insertFavorite(course)

    suspend fun removeFavorite(courseId: Int) = dao.deleteFavorite(courseId)

    //suspend fun isFavorite(courseId: Int): Boolean = dao.isFavorite(courseId)
}