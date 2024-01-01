package com.example.kotlinfolio

import com.prolificinteractive.materialcalendarview.CalendarDay
import android.net.Uri

data class GalleryImage(
    var img: Int,
    var title: String,
    var description: String,
    var uri: Uri?
    var date: CalendarDay

){}
