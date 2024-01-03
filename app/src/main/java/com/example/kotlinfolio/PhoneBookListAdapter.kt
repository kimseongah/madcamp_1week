package com.example.kotlinfolio

import Person
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prolificinteractive.materialcalendarview.CalendarDay



class PhoneBookListAdapter(var persons: ArrayList<Person>, var con: Context, private val listener: ContactFragment) :
    RecyclerView.Adapter<PhoneBookListAdapter.ViewHolder>() {
    var TAG = "PhoneBookListAdapter"
    interface AdapterListener {
        fun onImageEditButtonClicked(position: Int)
    }
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
    @SuppressLint("ResourceAsColor")
    fun returnToFragment(position: Int) {
        // 프래그먼트로 돌아가는 코드
        AlertDialog.Builder(con).apply {
            var person = persons[position]
            setTitle(person.name)

            val message = SpannableString("${person.phoneNumber}\n\n${person.data}\n${person.date.year}.${person.date.month}.${person.date.day}")

            // Apply a smaller size and gray color to the date part
            val startIndex = ("${person.phoneNumber}\n" +
                    "\n" +
                    "${person.data}\n").length
            message.setSpan(
                ForegroundColorSpan(R.color.Gray),
                startIndex,
                message.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            message.setSpan(
                AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE),
                startIndex,
                message.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            setMessage(message)

            setNeutralButton("DELETE") { _, _ ->
                // 다이얼로그가 뜰 때 어댑터의 showEditDialog 메서드를 호출
                deleteItem(position)
            }
            setPositiveButton("EDIT") { _, _ ->
                // 다이얼로그가 뜰 때 어댑터의 showEditDialog 메서드를 호출
                showEditDialog(position)
            }
            show()
        }
    }
    fun deleteItem(position: Int) {
        AlertDialog.Builder(con).apply {
            var person = persons[position]
            setTitle("연락처 삭제하기")
            setMessage("정말로 삭제하시겠습니까?")
            setNegativeButton("Yes") { _, _ ->
                persons.removeAt(position)
                notifyItemRemoved(position)
            }
            setPositiveButton("No") { _, _ ->

            }
            show()
        }
    }
    private fun loadImageIntoImageView(imageUri: String, imageView: ImageView) {
        Glide.with(con)
            .load(imageUri)
            .into(imageView)
    }
    fun showEditDialog(position: Int) {
        // 해당 position의 Person을 가져와서 편집 다이얼로그 띄우기
        val person = persons[position]

        // 다이얼로그의 커스텀 레이아웃을 inflate
        val dialogView = LayoutInflater.from(con).inflate(R.layout.edit_dialog_layout, null)
        val editTextData = dialogView.findViewById<EditText>(R.id.editTextData)
        editTextData.setText(person.data)

        val imageView = dialogView.findViewById<ImageView>(R.id.imageView)
        val imageEditButton = dialogView.findViewById<Button>(R.id.imageEditButton)

        // 이미지를 로드하여 imageView에 표시
        if(person.imagePath != null || person.imagePath != "none") loadImageIntoImageView(person.imagePath, imageView)
        else {
            Glide.with(con)
                .load(R.drawable.ic_person)
                .into(imageView)
        }

        // 이미지 편집 버튼 클릭 시 갤러리에서 이미지 선택
        imageEditButton.setOnClickListener {
            listener.onImageEditButtonClicked(position)
            loadImageIntoImageView(person.imagePath, imageView)
        }

        // 다이얼로그 생성
        AlertDialog.Builder(con)
            .setTitle("Edit Data")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                // "Save" 버튼 클릭 시 EditText의 내용을 가져와서 처리
                val editedData = editTextData.text.toString()

                // 데이터 수정
                person.data = editedData
                person.date = CalendarDay.today()

                // RecyclerView 갱신 (변경된 데이터를 반영하기 위해)
                notifyItemChanged(position)

                returnToFragment(position)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()  // 다이얼로그 닫기
            }
            .show()
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
        if((person.imagePath != null)){
            if(person.imagePath != "newFile") {
                Glide.with(holder.itemView.context)
                    .load(person.imagePath)
                    .into(holder.iv_person_phone_book_list_item)
            }
        }


    }

    override fun getItemCount(): Int {
        return persons.size
    }


}