package com.example.testEffectiveMobile.shared.courses.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteCourseDao {

    @Query("SELECT * FROM favorite_courses")
    suspend fun getAllFavorites(): List<FavoriteCourse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(course: FavoriteCourse)

    @Query("DELETE FROM favorite_courses WHERE id = :courseId")
    suspend fun deleteFavorite(courseId: Int)

    /*@Query("SELECT EXISTS(SELECT 1 FROM favorite_courses WHERE id = :courseId)")
    suspend fun isFavorite(courseId: Int): Boolean*/
}