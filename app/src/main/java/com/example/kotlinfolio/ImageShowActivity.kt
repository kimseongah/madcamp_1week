package com.example.kotlinfolio

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinfolio.databinding.ImageShowBinding

class ImageShowActivity : AppCompatActivity() {
    private val binding by lazy { ImageShowBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val isNull = intent.getIntExtra("IsNull", 0)
        if(isNull == 0){
            binding.shownImageView.setImageURI(Uri.parse(intent.getStringExtra("ImgUri").toString()))
        }
        else{
            binding.shownImageView.setImageResource(intent.getIntExtra("ImgRes",0))
        }

    }
}

