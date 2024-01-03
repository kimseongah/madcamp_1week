package com.example.kotlinfolio

import Person
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
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

    private val PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123


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
        ModelPreferencesManager.put(persons.size, "CountContact")
        for(i in 0 until persons.size){
            ModelPreferencesManager.put(persons[i].no, "Personno$i")
            ModelPreferencesManager.put(persons[i].name, "Personname$i")
            ModelPreferencesManager.put(persons[i].phoneNumber, "PersonphoneNumber$i")
            ModelPreferencesManager.put(persons[i].data, "Persondata$i")
            ModelPreferencesManager.put(persons[i].date.year, "Personyear$i")
            ModelPreferencesManager.put(persons[i].date.month, "Personmonth$i")
            ModelPreferencesManager.put(persons[i].date.day, "Personday$i")
        }
    }

    fun onImageEditButtonClicked(position: Int) {
        // 갤러리 열기 등의 처리 수행
        openGallery(position)
    }
    private fun openGallery(position: Int) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"
        val sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("position", position).apply()
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            val sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
            val position = sharedPreferences.getInt("position", -1)

            if (position != -1) {
                // 갤러리에서 이미지를 선택한 경우
                val selectedImageUri: Uri? = data.data

                // 선택한 이미지를 처리하는 로직 추가
                if (selectedImageUri != null) {
                    persons[position].imagePath = selectedImageUri.toString()
                    setData()
                }
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPhoneBook = binding.rvPhoneBook
        fabAdd = binding.fabAdd

        var checkedContactFrag : Boolean? = ModelPreferencesManager.get<Boolean>("checkedContactFrag")
        if (checkedContactFrag == null){
            persons = getPersons()
            ModelPreferencesManager.put(true, "checkedContactFrag")
            ModelPreferencesManager.put(persons.size, "CountContact")
            for(i in 0 until persons.size){
                ModelPreferencesManager.put(persons[i].no, "Personno$i")
                ModelPreferencesManager.put(persons[i].name, "Personname$i")
                ModelPreferencesManager.put(persons[i].phoneNumber, "PersonphoneNumber$i")
                ModelPreferencesManager.put(persons[i].data, "Persondata$i")
                ModelPreferencesManager.put(persons[i].date.year, "Personyear$i")
                ModelPreferencesManager.put(persons[i].date.month, "Personmonth$i")
                ModelPreferencesManager.put(persons[i].date.day, "Personday$i")
            }
        }
        else{
            persons = arrayListOf<Person>()
            var cnt : Int = ModelPreferencesManager.get<Int>("CountContact")!!
            for (i in 0 until cnt){
                val no = ModelPreferencesManager.get<Int>("Personno$i")!!
                val name = ModelPreferencesManager.get<String>("Personname$i")!!
                val phoneNumber = ModelPreferencesManager.get<String>("PersonphoneNumber$i")!!
                val data = ModelPreferencesManager.get<String>("Persondata$i")!!
                val dateyear = ModelPreferencesManager.get<Int>("Personyear$i")!!
                val datemonth = ModelPreferencesManager.get<Int>("Personmonth$i")!!
                val dateday = ModelPreferencesManager.get<Int>("Personday$i")!!
                persons.add(Person(no, name, phoneNumber, data, CalendarDay.from(dateyear, datemonth, dateday), "newFile"))
            }
        }

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
                val newPerson = Person(id, name, phoneNumber, data, date, "newFile")
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