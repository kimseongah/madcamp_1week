package com.example.kotlinfolio

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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
                returnToFragment(adapterPosition)
            }

        }
    }
    fun deleteItem(position: Int) {
        persons.removeAt(position)
        notifyItemRemoved(position)
    }
    fun showEditDialog(position: Int) {
        // 해당 position의 Person을 가져와서 편집 다이얼로그 띄우기
        val person = persons[position]

        // 다이얼로그의 커스텀 레이아웃을 inflate
        val dialogView = LayoutInflater.from(con).inflate(R.layout.edit_dialog_layout, null)

        val editTextData = dialogView.findViewById<EditText>(R.id.editTextData)
        editTextData.setText(person.data)

        // 다이얼로그 생성
        AlertDialog.Builder(con)
            .setTitle("Edit Data")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                // "Save" 버튼 클릭 시 EditText의 내용을 가져와서 처리
                val editedData = editTextData.text.toString()

                // 데이터 수정
                person.data = editedData

                // RecyclerView 갱신 (변경된 데이터를 반영하기 위해)
                notifyItemChanged(position)

                returnToFragment(position)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()  // 다이얼로그 닫기
            }
            .show()
    }
    fun showAddDialog(position: Int) {
        // 해당 position의 Person을 가져와서 편집 다이얼로그 띄우기
        val person = persons[position]

        // 다이얼로그의 커스텀 레이아웃을 inflate
        val dialogView = LayoutInflater.from(con).inflate(R.layout.edit_dialog_layout, null)

        // 다이얼로그 생성
        AlertDialog.Builder(con)
            .setTitle("Add Data")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                // "Save" 버튼 클릭 시 EditText의 내용을 가져와서 처리
                val editedData = dialogView.findViewById<EditText>(R.id.editTextData).text.toString()

                person.data += "\n" + editedData

                // RecyclerView 갱신 (변경된 데이터를 반영하기 위해)
                notifyItemChanged(position)

                returnToFragment(position)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()  // 다이얼로그 닫기
            }
            .show()
    }

    fun returnToFragment(position: Int) {
        // 프래그먼트로 돌아가는 코드
        AlertDialog.Builder(con).apply {
            var person = persons[position]
            setTitle(person.name)
            setMessage(person.phoneNumber+"\n"+person.data)
            setNeutralButton("EDIT") { _, _ ->
                // 다이얼로그가 뜰 때 어댑터의 showEditDialog 메서드를 호출
                showEditDialog(position)
            }
            setPositiveButton("ADD") { _, _ ->
                // 다이얼로그가 뜰 때 어댑터의 showEditDialog 메서드를 호출
                showAddDialog(position)
            }
            show()
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