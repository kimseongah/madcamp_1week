package com.example.kotlinfolio

import android.net.Uri
import com.prolificinteractive.materialcalendarview.CalendarDay

data class GalleryImage(
    var img: Int,
    var title: String,
    var description: String,
    var uri: Uri?,
    var date: CalendarDay

){}
