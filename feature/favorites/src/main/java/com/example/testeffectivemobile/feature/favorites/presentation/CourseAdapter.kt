package com.example.testeffectivemobile.feature.favorites.presentation

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testEffectiveMobile.common.resources.databinding.ItemCourseBinding
import com.example.testEffectiveMobile.shared.courses.data.local.FavoriteCourse
import com.example.testEffectiveMobile.util.validation.formatDate

class CourseAdapter(
    private val onBookmarkClick: (FavoriteCourse) -> Unit
) : ListAdapter <FavoriteCourse, CourseAdapter.CourseViewHolder>(DiffCallback()) {

    private var currentCourses: List<FavoriteCourse> = emptyList()

    override fun submitList(list: List<FavoriteCourse>?) {
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
        fun bind(course:FavoriteCourse) {
            binding.textTitle.text = course.title
            binding.textDescription.text = course.text

            binding.textPrice.text = "${course.price}₽"
            binding.ratingText.text = course.rate
            binding.dateText.text = formatDate(course.publishDate)
            binding.bookmarkButton.isSelected = course.hasLike
            val context = binding.root.context

            val imageResId = context.resources.getIdentifier(
                course.imageUrl,
                "drawable",
                context.packageName
            )
            binding.backgroundImage.setImageResource(imageResId)

            binding.bookmarkButton.setOnClickListener {
                val updatedCourse = course.copy(hasLike = !course.hasLike)
                onBookmarkClick(updatedCourse)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FavoriteCourse>() {
        override fun areItemsTheSame(oldItem: FavoriteCourse, newItem:FavoriteCourse): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FavoriteCourse, newItem: FavoriteCourse): Boolean =
            oldItem == newItem
    }
}