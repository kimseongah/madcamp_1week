package com.example.kotlinfolio

import com.prolificinteractive.materialcalendarview.CalendarDay

data class Person(
    var no: Int,
    var name: String,
    var phoneNumber: String,
    var data: String,
    var date: CalendarDay,
    var imagePath: String = "none"
){}