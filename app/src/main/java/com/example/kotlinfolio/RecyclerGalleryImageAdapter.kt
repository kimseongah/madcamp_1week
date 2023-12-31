package com.example.kotlinfolio

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class RecyclerGalleryImageAdapter(val items: MutableList<GalleryImage>, var con: Context) :
    RecyclerView.Adapter<RecyclerGalleryImageAdapter.ViewHolder>(){
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var itemClickListener: onItemClickListener

    fun setItemClickListener(itemClickListener: onItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerGalleryImageAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_galleryimage, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerGalleryImageAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
            fun bindItems(items: GalleryImage){
                val imageArea = itemView.findViewById<ImageView>(R.id.imageArea)

                imageArea.setImageResource(items.img)
            }

    }
}