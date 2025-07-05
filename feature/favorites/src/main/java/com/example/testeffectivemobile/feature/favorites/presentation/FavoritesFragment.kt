package com.example.testeffectivemobile.feature.favorites.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testeffectivemobile.feature.favorites.R
import com.example.testeffectivemobile.feature.favorites.databinding.FragmentFavoritesBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val favoritesViewModel: FavoriteCoursesViewModel by viewModel()

    private lateinit var adapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CourseAdapter { updatedCourse ->
            if (updatedCourse.hasLike) {
                favoritesViewModel.addCourseToFavorites(updatedCourse)
            } else {
                favoritesViewModel.removeCourseFromFavorites(updatedCourse.id)
            }
        }

        binding.posterList.layoutManager = LinearLayoutManager(requireContext())
        binding.posterList.adapter = adapter

        favoritesViewModel.loadFavorites()

        val progressBar = binding.progressBar

        viewLifecycleOwner.lifecycleScope.launch {
            favoritesViewModel.favorites.collectLatest { favoriteCourses ->
                progressBar.visibility = if (favoriteCourses.isEmpty()) View.VISIBLE else View.GONE
                adapter.submitList(favoriteCourses)
                updateEmptyState(favoriteCourses.isEmpty())
            }
        }

    }
    private fun updateEmptyState(isEmpty: Boolean) {
        if (isEmpty) {
            binding.progressBar.visibility=View.GONE
            binding.posterList.visibility = View.GONE
            binding.emptyTextView.visibility = View.VISIBLE
        } else {
            binding.posterList.visibility = View.VISIBLE
            binding.emptyTextView.visibility = View.GONE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}