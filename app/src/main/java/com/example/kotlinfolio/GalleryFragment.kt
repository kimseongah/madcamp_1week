package com.example.kotlinfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfolio.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {
    private lateinit var binding: FragmentGalleryBinding

    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var rvGallery: RecyclerView
    private lateinit var recyclerGalleryImageAdapter: RecyclerGalleryImageAdapter
    private lateinit var images:MutableList<GalleryImage>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvGallery = binding.recyclerGalleryView
        images = mutableListOf<GalleryImage>()
        images.add(GalleryImage(R.drawable.exampleimage1))
        images.add(GalleryImage(R.drawable.exampleimage2))
        images.add(GalleryImage(R.drawable.exampleimage3))
        images.add(GalleryImage(R.drawable.exampleimage4))
        sharedViewModel.images = images
        setAdapter()
    }

    private fun setAdapter(){
        rvGallery.layoutManager = GridLayoutManager(context, 2)
        recyclerGalleryImageAdapter = context?.let { RecyclerGalleryImageAdapter(images) }!!
        rvGallery.adapter = recyclerGalleryImageAdapter

    }
}