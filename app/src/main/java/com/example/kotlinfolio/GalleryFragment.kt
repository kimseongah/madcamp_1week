package com.example.kotlinfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfolio.databinding.ActivityMainBinding
import com.example.kotlinfolio.databinding.FragmentGalleryBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.EditText

class GalleryFragment : Fragment() {
    private lateinit var binding: FragmentGalleryBinding

    private lateinit var rvGallery: RecyclerView
    private lateinit var recyclerGalleryImageAdapter: RecyclerGalleryImageAdapter
    private lateinit var images:MutableList<GalleryImage>
    private lateinit var fabAddGal: FloatingActionButton
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
        fabAddGal = binding.fabAddGal
        images = mutableListOf<GalleryImage>()
        images.add(GalleryImage(R.drawable.exampleimage1, "Example Image 1", "This is example 1."))
        images.add(GalleryImage(R.drawable.exampleimage2, "Example Image 2", "This is example 2."))
        images.add(GalleryImage(R.drawable.exampleimage3, "Example Image 3", "This is example 3."))
        images.add(GalleryImage(R.drawable.exampleimage4, "Example Image 4", "This is example 4."))

        setAdapter()

        fabAddGal.setOnClickListener{
            showAddGalleryDialog()
        }
    }

    private fun setAdapter(){
        rvGallery.layoutManager = GridLayoutManager(context, 2)
        recyclerGalleryImageAdapter = context?.let { RecyclerGalleryImageAdapter(images, it) }!!
        rvGallery.adapter = recyclerGalleryImageAdapter
    }

    private fun showAddGalleryDialog() {

    }
}