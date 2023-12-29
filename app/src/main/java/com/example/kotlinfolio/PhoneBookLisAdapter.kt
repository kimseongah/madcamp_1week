package com.example.kotlinfolio

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PhoneBookListAdapter(var persons: ArrayList<Person>, var con: Context) :
    RecyclerView.Adapter<PhoneBookListAdapter.ViewHolder>() {
    var TAG = "PhoneBookListAdapter"

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_person_phone_book_list_item: ImageView
        var tv_name_phone_book_list_item: TextView
        var tv_phone_number_phone_book_list_item: TextView

        init {
            iv_person_phone_book_list_item = itemView.findViewById(R.id.iv_person_phone_book_list_item)
            tv_name_phone_book_list_item = itemView.findViewById(R.id.tv_name_phone_book_list_item)
            tv_phone_number_phone_book_list_item = itemView.findViewById(R.id.tv_phone_number_phone_book_list_item)

            itemView.setOnClickListener {
                AlertDialog.Builder(con).apply {
                    var position = adapterPosition
                    var person = persons[position]
                    setTitle(person.name)
                    setMessage(person.phoneNumber+"\n"+person.data)
                    setPositiveButton("Edit", DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(con, "Edit Button Click", Toast.LENGTH_SHORT).show()
                    })
                    //TODO edit 기능도 있으면 좋겠지만... 넘 어려울 것 같음....
                    show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val con = parent.context
        val inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_phonebook, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person: Person = persons[position]
        holder.tv_name_phone_book_list_item.text = person.name
        holder.tv_phone_number_phone_book_list_item.text = person.phoneNumber
    }

    override fun getItemCount(): Int {
        return persons.size
    }


}