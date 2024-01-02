package com.example.kotlinfolio

import Person
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val persons = MutableLiveData<List<Person>>()
    val images = MutableLiveData<List<GalleryImage>>()
}

