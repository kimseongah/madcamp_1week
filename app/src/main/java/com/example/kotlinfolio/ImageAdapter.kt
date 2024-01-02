package com.example.kotlinfolio

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinfolio.databinding.ItemImageBinding

class ImageAdapter(private val context: Context, private val images: List<GalleryImage>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: GalleryImage) {
            if (image.uri == null) {
                Glide.with(context)
                    .load(image.img)
                    .into(binding.imageView)
            } else {
                Glide.with(context)
                    .load(image.uri)
                    .into(binding.imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        holder.bind(image)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}