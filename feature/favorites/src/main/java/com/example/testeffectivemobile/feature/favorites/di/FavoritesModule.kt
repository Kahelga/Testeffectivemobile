package com.example.testeffectivemobile.feature.favorites.di
import androidx.room.Room
import com.example.testEffectiveMobile.shared.courses.data.local.AppDatabase
import com.example.testEffectiveMobile.shared.courses.data.local.FavoriteCourseRepository
import com.example.testeffectivemobile.feature.favorites.presentation.FavoriteCoursesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "favorites_database"
        ).build()
    }

    single { get<AppDatabase>().favoriteCourseDao() }
    single { FavoriteCourseRepository(get()) }
     viewModel{ FavoriteCoursesViewModel(get()) }
}
