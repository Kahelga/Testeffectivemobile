package com.example.testEffectiveMobile.shared.courses.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCourse::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCourseDao(): FavoriteCourseDao
}