package com.example.kotlinfolio

import Person
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfolio.databinding.ContactBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.lang.reflect.Type
import java.time.ZoneId
import java.util.Locale


class ContactFragment : Fragment() {
    private lateinit var binding : ContactBinding

    private lateinit var rvPhoneBook: RecyclerView
    private lateinit var phoneBookListAdapter: PhoneBookListAdapter
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var persons:ArrayList<Person>
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val PICK_IMAGE_REQUEST = 1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContactBinding.inflate(layoutInflater)
        return binding.root
    }
    private fun setData() {
        // persons와 images를 설정하고자 할 때
        sharedViewModel.persons.value = persons/* 설정할 images 리스트 */
    }

    fun onImageEditButtonClicked(position: Int) {
        // 갤러리 열기 등의 처리 수행
        openGallery()
        // 선택한 이미지 경로를 저장할 수도 있음
        // 저장 후 Adapter에게 이벤트 전달하여 이미지 경로를 업데이트하도록 할 수 있음
    }
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPhoneBook = binding.rvPhoneBook
        fabAdd = binding.fabAdd
        persons = getPersons()
        setData()
        setAdapter()

        // FloatingActionButton 클릭 이벤트 핸들러 추가
        fabAdd.setOnClickListener {
            // 클릭 이벤트 처리 (다이얼로그 표시)
            showAddContactDialog()
        }
    }

    private fun setAdapter(){
        rvPhoneBook.layoutManager = LinearLayoutManager(context)
        phoneBookListAdapter = PhoneBookListAdapter(persons, requireContext(), this)
        rvPhoneBook.adapter = phoneBookListAdapter
    }

    private fun getPersons(): ArrayList<Person> {
        val jsonString: String = requireActivity().assets.open("data.json")
            .bufferedReader()
            .use { it.readText() }

        val gson = GsonBuilder()
            .registerTypeAdapter(CalendarDay::class.java,
                ContactFragment.CalendarDayDeserializer()
            )
            .create()
        val listPersonType = object : TypeToken<List<Person>>() {}.type
        return gson.fromJson(jsonString, listPersonType)
    }
    private fun showAddContactDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.add_contact_dialog_layout, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
        val editTextPhoneNumber = dialogView.findViewById<EditText>(R.id.editTextPhoneNumber)
        val editTextData = dialogView.findViewById<EditText>(R.id.editTextData)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Contact")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val id = persons.size
                val name = editTextName.text.toString()
                val phoneNumber = editTextPhoneNumber.text.toString()
                val data = editTextData.text.toString()
                val date = CalendarDay.today()

                // Add the new contact to the list
                val newPerson = Person(id, name, phoneNumber, data, date)
                persons.add(newPerson)
                setData()
                // Notify the adapter that the data has changed
                phoneBookListAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    class CalendarDayDeserializer : JsonDeserializer<CalendarDay> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): CalendarDay {
            val dateString = json?.asString ?: ""
            return convertStringToCalendarDay(dateString, "yyyy-MM-dd") ?: CalendarDay.today()
        }

        private fun convertStringToCalendarDay(
            dateString: String,
            dateFormat: String
        ): CalendarDay? {
            return try {
                val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
                val date = sdf.parse(dateString)
                if (date != null) {
                    val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    CalendarDay.from(localDate.year, localDate.monthValue, localDate.dayOfMonth)
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }


    }

}