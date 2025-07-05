package com.example.testeffectivemobile.feature.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testEffectiveMobile.shared.courses.data.local.FavoriteCourse
import com.example.testEffectiveMobile.shared.courses.data.local.FavoriteCourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteCoursesViewModel(
    private val repository: FavoriteCourseRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<FavoriteCourse>>(emptyList())
    val favorites: StateFlow<List<FavoriteCourse>> = _favorites

   val imageResNameMap = mapOf(
        100 to "cover1",
        101 to "cover2",
        102 to "cover3"
    )

    fun loadFavorites() {
        viewModelScope.launch {
            val list = repository.getFavorites()
            _favorites.value = list
        }
    }

    fun addCourseToFavorites(course: FavoriteCourse) {
        viewModelScope.launch {
            repository.addFavorite(course)
            loadFavorites()
        }
    }

    fun removeCourseFromFavorites(courseId: Int) {
        viewModelScope.launch {
            repository.removeFavorite(courseId)
            loadFavorites()
        }
    }
}