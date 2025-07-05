package com.example.testEffectiveMobile.feature.courses.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testEffectiveMobile.shared.courses.data.local.FavoriteCourse
import com.example.testEffectiveMobile.shared.courses.data.local.FavoriteCourseRepository
import com.example.testEffectiveMobile.shared.courses.domain.usecase.GetCoursesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class HomeViewModel(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val favoriteCourseRepository: FavoriteCourseRepository
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Initial)
    val state: StateFlow<HomeState> = _state

    private val imageResNameMap = mapOf(
        100 to "cover1",
        101 to "cover2",
        102 to "cover3"
    )

   fun loadCourses() {
       viewModelScope.launch {
           _state.value = HomeState.Loading
           try {
               val coursesFromNetwork = getCoursesUseCase()

               val favoritesFromDb = favoriteCourseRepository.getFavorites()
               val favoriteIdsFromDb = favoritesFromDb.map { it.id }.toSet()

               //чтобы соответсвовало бд с избранными
               val updatedCourses = coursesFromNetwork.courses.map { course ->
                   course.copy(hasLike = favoriteIdsFromDb.contains(course.id))
               }

               val updatedCoursesFromNetwork = coursesFromNetwork.copy(courses = updatedCourses)

               _state.value = HomeState.Content(updatedCoursesFromNetwork)
           } catch (ce: CancellationException) {
               throw ce
           } catch (ex: Exception) {
               _state.value = HomeState.Failure(ex.localizedMessage.orEmpty())
           }
       }
   }
//наоборот удаляет из бд данные,которые не соответсвую ответу от сервера
    /* fun loadCourses() {
         viewModelScope.launch {
             _state.value = HomeState.Loading
             try {
                 val coursesFromNetwork = getCoursesUseCase()

                 val favoritesFromDb = favoriteCourseRepository.getFavorites()

                 val favoriteIdsFromDb = favoritesFromDb.map { it.id }.toSet()
                 coursesFromNetwork.courses.filter { it.hasLike }
                     .filter { !favoriteIdsFromDb.contains(it.id) }
                     .forEach { course ->
                         val favorite = FavoriteCourse(
                             id = course.id,
                             title = course.title,
                             text = course.text,
                             price = course.price,
                             rate = course.rate,
                             startDate = course.startDate,
                             hasLike = true,
                             publishDate = course.publishDate,
                             imageUrl = imageResNameMap[course.id] ?: "cover1"
                         )
                         favoriteCourseRepository.addFavorite(favorite)
                     }

                 coursesFromNetwork.courses
                     .filter { !it.hasLike }
                     .filter { favoriteIdsFromDb.contains(it.id) }
                     .forEach { course ->
                         favoriteCourseRepository.removeFavorite(course.id)
                     }

                 _state.value = HomeState.Content(coursesFromNetwork)
             } catch (ce: CancellationException) {
                 throw ce
             } catch (ex: Exception) {
                 _state.value = HomeState.Failure(ex.localizedMessage.orEmpty())
             }
         }
     }*/


}