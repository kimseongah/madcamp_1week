package com.example.kotlinfolio

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar

class RecyclerGalleryImageAdapter(val items: MutableList<GalleryImage>, var con: Context) :
    RecyclerView.Adapter<RecyclerGalleryImageAdapter.ViewHolder>(){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerGalleryImageAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_galleryimage, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerGalleryImageAdapter.ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    fun deleteGallery(position: Int) {
        AlertDialog.Builder(con).apply {
            setTitle("이미지 삭제하기")
            setMessage("정말로 삭제하시겠습니까?")
            setNegativeButton("Yes") { _, _ ->
                items.removeAt(position)
                notifyItemRemoved(position)
                ModelPreferencesManager.put(GalleryImageList(items), "SavedList")
            }
            setPositiveButton("No") { _, _ ->

            }
            show()
        }
    }

    @SuppressLint("SetTextI18n")
    fun editGallery(position: Int) {
        val imgData = items[position]

        val dialogView = LayoutInflater.from(con).inflate(R.layout.edit_image_layout, null)

        val editTitleData = dialogView.findViewById<EditText>(R.id.editTitleData)
        val editDesData = dialogView.findViewById<EditText>(R.id.editDesData)
        val DateData = dialogView.findViewById<TextView>(R.id.dateText)
        val imgbtn = dialogView.findViewById<ImageButton>(R.id.imageButton)
        var final_year = imgData.date.year
        var final_month = imgData.date.month
        var final_day = imgData.date.day
        editTitleData.setText(imgData.title)
        editDesData.setText(imgData.description)
        DateData.setText("${final_year}년 ${final_month}월 ${final_day}일")

        imgbtn.setOnClickListener{
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val dateString = "${year}년 ${month+1}월 ${dayOfMonth}일"
                DateData.text = dateString
                final_year = year
                final_month = month+1
                final_day = dayOfMonth
            }
            DatePickerDialog(con, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(
                Calendar.DAY_OF_MONTH)).show()
        }

        AlertDialog.Builder(con)
            .setTitle("Edit Data")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->

                val editedTitle = editTitleData.text.toString()
                val editedDes = editDesData.text.toString()

                imgData.title = editedTitle
                imgData.description = editedDes
                imgData.date = CalendarDay.from(final_year, final_month, final_day)

                notifyItemChanged(position)
                ModelPreferencesManager.put(GalleryImageList(items), "SavedList")

                itemClicked(position)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun expandGallery(position: Int) {
        val intent = Intent(con, ImageShowActivity::class.java)
        intent.putExtra("ImgRes", items[position].img)
        con.startActivity(intent)

    }

    fun itemClicked(position: Int) {
        AlertDialog.Builder(con).apply {
            var imageData = items[position]
            setTitle(imageData.title)
            var dateString = imageData.date.year.toString() + "-" + imageData.date.month.toString() + "-" + imageData.date.day.toString()
            setMessage(imageData.description + "\n" + dateString)
            setNegativeButton("DELETE") { _, _ ->
                deleteGallery(position)
            }
            setPositiveButton("EDIT") { _, _ ->
                editGallery(position)
            }
            setNeutralButton("CLOSE"){ dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
            fun bindItems(items: GalleryImage){
                val imageArea = itemView.findViewById<ImageView>(R.id.imageArea)
                imageArea.setImageBitmap(items.img)
                itemView.setOnClickListener {
                    expandGallery(adapterPosition)
                }
                itemView.setOnLongClickListener {
                    itemClicked(adapterPosition)
                    return@setOnLongClickListener true
                }
            }

    }
}