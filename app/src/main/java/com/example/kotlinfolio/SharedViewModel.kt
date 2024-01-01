package com.example.kotlinfolio

import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var images: List<GalleryImage> = emptyList()
}
