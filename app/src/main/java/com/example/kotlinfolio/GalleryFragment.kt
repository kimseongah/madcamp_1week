package com.example.kotlinfolio

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfolio.databinding.FragmentGalleryBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.prolificinteractive.materialcalendarview.CalendarDay

class GalleryFragment : Fragment() {
    private lateinit var binding: FragmentGalleryBinding

    private lateinit var rvGallery: RecyclerView
    private lateinit var recyclerGalleryImageAdapter: RecyclerGalleryImageAdapter
    private lateinit var images:MutableList<GalleryImage>
    private lateinit var fabAddGal: FloatingActionButton

    private val PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123
    private val GALLERY_REQUEST_CODE = 10
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(layoutInflater)

        binding.fabAddGal.setOnClickListener  {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                )
            } else {
                getImageFromGallery()
            }
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedImageUri = data.data
                if (selectedImageUri != null) {
                    val newGalleryImage = GalleryImage(0, "Title " + (images.size + 1).toString(), "Description " + (images.size + 1).toString(), selectedImageUri, CalendarDay.today())
                    images.add(newGalleryImage)
                    recyclerGalleryImageAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvGallery = binding.recyclerGalleryView
        fabAddGal = binding.fabAddGal


        images = mutableListOf<GalleryImage>()

        images.add(GalleryImage(R.drawable.exampleimage1, "Example Image 1", "This is example 1.", null, CalendarDay.from(2023, 12, 25)))
        images.add(GalleryImage(R.drawable.exampleimage2, "Example Image 2", "This is example 2.", null, CalendarDay.from(2023, 12, 22)))
        images.add(GalleryImage(R.drawable.exampleimage3, "Example Image 3", "This is example 3.", null, CalendarDay.from(2023, 12, 31)))
        images.add(GalleryImage(R.drawable.exampleimage4, "Example Image 4", "This is example 4.", null, CalendarDay.from(2023, 12, 28)))
        setFragmentResult("requestKey", bundleOf("images" to images))
        setAdapter()




    }

    private fun setAdapter(){
        rvGallery.layoutManager = GridLayoutManager(context, 2)
        recyclerGalleryImageAdapter = context?.let { RecyclerGalleryImageAdapter(images, it) }!!
        rvGallery.adapter = recyclerGalleryImageAdapter

    }

    private fun getImageFromGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

}