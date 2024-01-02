package com.example.kotlinfolio

import Person
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.kotlinfolio.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class CalendarFragment : Fragment() {
    private lateinit var binding : FragmentCalendarBinding
    private lateinit var images: MutableList<GalleryImage>
    private lateinit var persons: MutableList<Person>
    private val sharedViewModel: SharedViewModel by activityViewModels()

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

        sharedViewModel.persons.observe(viewLifecycleOwner) { newPersons ->
            persons = newPersons.toMutableList()
        }
        sharedViewModel.images.observe(viewLifecycleOwner) { newImages ->
            images = newImages.toMutableList()
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
            val textForDate = persons.filter { it.date == date }
            if (imagesForDate.isNotEmpty() or textForDate.isNotEmpty()) {
                // Display the images in some way (e.g., show in an ImageView)
                displayData(imagesForDate, textForDate)
            } else{
                Toast.makeText(requireContext(), "기록이 없습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private class TodayDecorator(private  val context: Context) : DayViewDecorator {
        private val today: CalendarDay = CalendarDay.today()
        private val textColor: Int = context.resources.getColor(R.color.purple_500)
        private val boldTypeface: Typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)


        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day == today
        }

        @RequiresApi(Build.VERSION_CODES.P)
        override fun decorate(view: DayViewFacade) {
            view.addSpan(ForegroundColorSpan(textColor))
            view.addSpan(TypefaceSpan(boldTypeface))
        }
    }

    fun displayData(images: List<GalleryImage>, texts: List<Person>) {
        val displayDialog = DisplayDialog(requireContext(), images, texts)
        displayDialog.show()
    }

}