package com.example.kotlinfolio

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

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
            }
            setPositiveButton("No") { _, _ ->

            }
            show()
        }
    }

    fun editGallery(position: Int) {
        val imgData = items[position]

        val dialogView = LayoutInflater.from(con).inflate(R.layout.edit_image_layout, null)

        val editTitleData = dialogView.findViewById<EditText>(R.id.editTitleData)
        val editDesData = dialogView.findViewById<EditText>(R.id.editDesData)
        editTitleData.setText(imgData.title)
        editDesData.setText(imgData.description)

        AlertDialog.Builder(con)
            .setTitle("Edit Data")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->

                val editedTitle = editTitleData.text.toString()
                val editedDes = editDesData.text.toString()

                imgData.title = editedTitle
                imgData.description = editedDes

                notifyItemChanged(position)

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
        if(items[position].uri == null) {
            intent.putExtra("IsNull", 1)
        }
        else{
            intent.putExtra("IsNull", 0)
            intent.putExtra("ImgUri", items[position].uri.toString())
        }
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
                if(items.uri == null){
                    imageArea.setImageResource(items.img)
                }
                else{
                    imageArea.setImageURI(items.uri)
                }
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