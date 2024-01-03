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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
    private val sharedViewModel: SharedViewModel by activityViewModels()


    private val PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123
    private val GALLERY_REQUEST_CODE = 10
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private fun setData() {
        // persons와 images를 설정하고자 할 때
        sharedViewModel.images.value = images/* 설정할 images 리스트 */
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
                    val i = images.size - 1
                    ModelPreferencesManager.put("Title " + (images.size + 1).toString(), "Title$i")
                    ModelPreferencesManager.put("Description " + (images.size + 1).toString(), "Des$i")
                    ModelPreferencesManager.put(CalendarDay.today().year, "Year$i")
                    ModelPreferencesManager.put(CalendarDay.today().month, "Month$i")
                    ModelPreferencesManager.put(CalendarDay.today().day, "Day$i")
                    ModelPreferencesManager.put(0, "Img$i")
                    ModelPreferencesManager.put(selectedImageUri.toString(), "Uri$i")
                    ModelPreferencesManager.put(images.size, "Count")
                    setData()
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
        /*
        images.add(GalleryImage(R.drawable.exampleimage1, "Example Image 1", "This is example 1.", null, CalendarDay.from(2023, 12, 25)))
        images.add(GalleryImage(R.drawable.exampleimage2, "Example Image 2", "This is example 2.", null, CalendarDay.from(2024, 1, 1)))
        images.add(GalleryImage(R.drawable.exampleimage3, "Example Image 3", "This is example 3.", null, CalendarDay.from(2023, 12, 31)))
        images.add(GalleryImage(R.drawable.exampleimage4, "Example Image 4", "This is example 4.", null, CalendarDay.from(2023, 12, 31)))
         */
        var checkedGalleryFrag : Boolean? = ModelPreferencesManager.get<Boolean>("checkedGalleryFrag")
        if (checkedGalleryFrag == null){
            images.add(GalleryImage(R.drawable.exampleimage1, "Example Image 1", "This is example 1.", null, CalendarDay.from(2023, 12, 25)))
            images.add(GalleryImage(R.drawable.exampleimage2, "Example Image 2", "This is example 2.", null, CalendarDay.from(2024, 1, 1)))
            images.add(GalleryImage(R.drawable.exampleimage3, "Example Image 3", "This is example 3.", null, CalendarDay.from(2023, 12, 31)))
            images.add(GalleryImage(R.drawable.exampleimage4, "Example Image 4", "This is example 4.", null, CalendarDay.from(2023, 12, 31)))
            ModelPreferencesManager.put(true, "checkedGalleryFrag")
        }
        else{
            var cnt : Int = ModelPreferencesManager.get<Int>("Count")!!
            for (i in 0 until cnt){
                //val des = ModelPreferencesManager.get<>()
                //images.add()
            }
        }

        setData()
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