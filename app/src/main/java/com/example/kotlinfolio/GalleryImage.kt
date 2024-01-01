package com.example.kotlinfolio

import android.net.Uri

data class GalleryImage(
    var img: Int,
    var title: String,
    var description: String,
    var uri: Uri?

){}
