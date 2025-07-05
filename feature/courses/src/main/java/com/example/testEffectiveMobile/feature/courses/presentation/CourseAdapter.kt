package com.example.testEffectiveMobile.feature.courses.presentation

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testEffectiveMobile.common.resources.databinding.ItemCourseBinding
import com.example.testEffectiveMobile.common.resources.R
import com.example.testEffectiveMobile.shared.courses.domain.entity.Course
import com.example.testEffectiveMobile.util.validation.formatDate

class CourseAdapter(
    private val onFavoriteClick: (Course, Boolean) -> Unit
) : ListAdapter<Course, CourseAdapter.CourseViewHolder>(DiffCallback()) {


    private var currentCourses: List<Course> = emptyList()

    override fun submitList(list: List<Course>?) {
        super.submitList(list)
        currentCourses = list ?: emptyList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CourseViewHolder(private val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(course: Course) {
            binding.textTitle.text = course.title
            binding.textDescription.text = course.text
            binding.textPrice.text = "${course.price}₽"
            binding.ratingText.text = course.rate
            binding.dateText.text = formatDate(course.publishDate)
            binding.bookmarkButton.isSelected = course.hasLike

            when (adapterPosition) {
                0 -> binding.backgroundImage.setImageResource(R.drawable.cover1)
                1 -> binding.backgroundImage.setImageResource(R.drawable.cover2)
                2 -> binding.backgroundImage.setImageResource(R.drawable.cover3)
                else -> {}
            }
            binding.bookmarkButton.setOnClickListener {
                val newHasLike = !course.hasLike
                val updatedCourse = course.copy(hasLike = !course.hasLike)
                val newList = currentCourses.toMutableList()
                newList[adapterPosition] = updatedCourse
                submitList(newList)
                onFavoriteClick(updatedCourse, newHasLike)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean =
            oldItem == newItem
    }
}