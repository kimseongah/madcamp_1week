package com.example.kotlinfolio

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.example.kotlinfolio.databinding.DialogImageGalleryBinding
import com.example.kotlinfolio.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class CalendarFragment : Fragment() {
    private lateinit var binding : FragmentCalendarBinding
    private lateinit var images: MutableList<GalleryImage>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("requestKey") { _, result ->
            images = result.getParcelableArrayList("images")!!

            // 받은 데이터 사용
        }
        setupCalendar()

    }

    private fun setupCalendar() {
        val calendarView = binding.calendarView

        // Create a decorator to highlight today's date
        val todayDecorator = TodayDecorator(binding.root.context)

        // Add the decorator to the calendar view
        calendarView.addDecorator(todayDecorator)

        // Set a date click listener to handle clicks on specific dates
        calendarView.setOnDateChangedListener { _, date, _ ->
            // Check if the date has images in the sharedViewModel's images list
            val imagesForDate = images.filter { it.date == date }
            if (imagesForDate.isNotEmpty()) {
                // Display the images in some way (e.g., show in an ImageView)
                displayImages(requireContext(), imagesForDate)
            }
        }
    }
    private class TodayDecorator(private  val context: Context) : DayViewDecorator {
        private val today: CalendarDay = CalendarDay.today()
        private val drawable: Drawable = context?.resources?.getDrawable(R.drawable.calendar_circle_gray) ?: throw IllegalStateException("Context is null")

        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day == today
        }

        override fun decorate(view: DayViewFacade) {
            // Apply the circle drawable to highlight today's date
            drawable?.let {
                view.setSelectionDrawable(it)
            }
        }
    }

    fun displayImages(context: Context, images: List<GalleryImage>) {
        val binding = DialogImageGalleryBinding.inflate(LayoutInflater.from(context))

        // Display the first image in the list using Glide and the binding
        if (images.isNotEmpty()) {
            Glide.with(context)
                .load(images[0].img)
                .into(binding.imageView)
        }

        val dialog = AlertDialog.Builder(context)
            .setView(binding.root)
            .create()

        binding.closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}