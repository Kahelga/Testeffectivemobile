package com.example.testEffectiveMobile.feature.courses.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testEffectiveMobile.feature.courses.R
import com.example.testEffectiveMobile.feature.courses.databinding.FragmentHomeBinding
import com.example.testEffectiveMobile.shared.courses.data.local.FavoriteCourse
import com.example.testEffectiveMobile.util.validation.parseDate
import com.example.testeffectivemobile.feature.favorites.presentation.FavoriteCoursesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class HomeFragment: Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()
    private val favoriteViewModel: FavoriteCoursesViewModel by viewModel()

    private lateinit var adapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CourseAdapter { course, isFavorite ->
            if (isFavorite) {
                val favorite = FavoriteCourse(
                    id = course.id,
                    title = course.title,
                    text = course.text,
                    price = course.price,
                    rate = course.rate,
                    startDate = course.startDate,
                    hasLike = true,
                    publishDate = course.publishDate,
                    imageUrl = favoriteViewModel.imageResNameMap[course.id] ?:"cover1"
                )
                favoriteViewModel.addCourseToFavorites(favorite)
            } else {
                favoriteViewModel.removeCourseFromFavorites(course.id)
            }
        }

        binding.posterList.layoutManager = LinearLayoutManager(requireContext())
        binding.posterList.adapter = adapter

        homeViewModel.loadCourses()
        val progressBar = binding.progressBar

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.state.collectLatest { state ->
                when (state) {
                    is HomeState.Initial -> {
                        progressBar.visibility = View.GONE
                    }
                    is HomeState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is HomeState.Content -> {
                        progressBar.visibility = View.GONE
                        adapter.submitList(state.courses.courses)
                    }
                    is HomeState.Failure -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.sortButton.setOnClickListener {
            val currentList = adapter.currentList
            val sortedList = currentList.sortedByDescending { course ->
                parseDate(course.publishDate) ?: LocalDate.MIN
            }
            adapter.submitList(sortedList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}