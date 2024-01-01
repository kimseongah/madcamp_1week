package com.example.kotlinfolio

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinfolio.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class CalendarFragment : Fragment() {
    private lateinit var binding : FragmentCalendarBinding
    private lateinit var sharedViewModel: SharedViewModel

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

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

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

    private fun displayImages(images: List<GalleryImage>) {
        // Implement your logic to display the images (e.g., show in an ImageView)
        // You can use the images list to display them
    }

}