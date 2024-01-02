package com.example.kotlinfolio

import Person
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinfolio.databinding.DialogImageGalleryBinding

class DisplayDialog(context: Context, private val images: List<GalleryImage>, private  val texts: List<Person>) : Dialog(context) {

    private var _binding: DialogImageGalleryBinding? = null
    private val binding get() = _binding!!
    private var contentAdapter: ContentAdapter


    init {
        // 커스텀 생성자에서 초기화 로직 수행
        _binding = DialogImageGalleryBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        contentAdapter = ContentAdapter(context, images, texts)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contentAdapter
        }

        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        // 다이얼로그의 크기를 동적으로 조절
        val width = context.resources.displayMetrics.widthPixels * 0.8
        val maxHeight = context.resources.displayMetrics.heightPixels * 0.7

        // 사진이 여러 개인 경우 높이의 60%를 사용하고, 하나인 경우 wrap_content로 설정
        val height = if (images.size > 1) {
            maxHeight.toInt()
        } else {
            ViewGroup.LayoutParams.WRAP_CONTENT
        }

        window?.setLayout(width.toInt(), height)
    }

}
