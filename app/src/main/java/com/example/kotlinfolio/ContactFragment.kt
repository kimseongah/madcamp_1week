package com.example.kotlinfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfolio.databinding.ContactBinding


class ContactFragment : Fragment() {
    private lateinit var rvPhoneBook: RecyclerView
    private lateinit var phoneBookListAdapter: PhoneBookListAdapter
    private lateinit var persons:Array<Person>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : ContactBinding = DataBindingUtil.inflate(inflater, R.layout.contact, container, false)

        val view: View = inflater.inflate(R.layout.contact, container, false)
        rvPhoneBook = view.findViewById(R.id.rv_phone_book)

        persons = tempPersons()
        setAdapter()

        return binding.root
    }

    private fun setAdapter(){
        //리사이클러뷰에 리사이클러뷰 어댑터 부착
        rvPhoneBook.layoutManager = LinearLayoutManager(context)
        phoneBookListAdapter = context?.let { PhoneBookListAdapter(persons, it) }!!
        rvPhoneBook.adapter = phoneBookListAdapter
    }

    private fun tempPersons(): Array<Person> {
        return arrayOf(
            Person(1, "kim", "01011111111"),
            Person(2, "lee", "01022222222"),
            Person(3, "park", "01033333333"),
            Person(4, "son", "01044444444"),
            Person(5, "hwang", "01055555555"),
            Person(6, "jo", "01066666666"),
            Person(7, "gwak", "01077777777"),
            Person(8, "sim", "01088888888"),
            Person(9, "choi", "01099999999"),
        )
    }
}