package com.example.kotlinfolio

data class Person(
    var no: Int,
    var name: String,
    var phoneNumber: String,
    var imagePath: String = "none",
){}