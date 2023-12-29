package com.example.kotlinfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfolio.databinding.ContactBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class ContactFragment : Fragment() {
    private lateinit var binding : ContactBinding

    private lateinit var rvPhoneBook: RecyclerView
    private lateinit var phoneBookListAdapter: PhoneBookListAdapter
    private lateinit var persons:ArrayList<Person>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ContactBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPhoneBook = binding.rvPhoneBook
        persons = getPersons()
        setAdapter()

    }

    private fun setAdapter(){
        rvPhoneBook.layoutManager = LinearLayoutManager(context)
        phoneBookListAdapter = context?.let { PhoneBookListAdapter(persons, it) }!!
        rvPhoneBook.adapter = phoneBookListAdapter
    }

    private fun getPersons(): ArrayList<Person> {
        val jsonString: String = requireActivity().assets.open("data.json")
            .bufferedReader()
            .use { it.readText() }

        val gson = Gson()
        val listPersonType = object : TypeToken<List<Person>>() {}.type
        var persons_ = gson.fromJson<ArrayList<Person>?>(jsonString, listPersonType)
        return persons_
    }


}