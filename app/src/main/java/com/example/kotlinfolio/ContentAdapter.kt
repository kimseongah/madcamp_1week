package com.example.kotlinfolio

import Person
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinfolio.databinding.ItemImageBinding

class ContentAdapter(
    private val context: Context,
    private val images: List<GalleryImage>,
    private val texts: List<Person>
) : RecyclerView.Adapter<ContentAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: GalleryImage?, text: Person?) {
            // 이미지를 처리
            if (image != null) {
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

            // 텍스트를 처리
            if (text != null) {
                binding.textView.text = "${text.name}: ${text.data}"

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        // 이미지와 텍스트의 인덱스를 각각 가져옴
        val image = if (position < images.size) images[position] else null
        val text = if (position < texts.size) texts[position] else null

        // 뷰홀더에 이미지와 텍스트를 전달하여 바인딩
        holder.bind(image, text)

        if (image != null && text != null) {
            // 간격 조절
            val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = context.resources.getDimensionPixelSize(R.dimen.image_text_margin)
            holder.itemView.layoutParams = layoutParams
        }
    }

    override fun getItemCount(): Int {
        // 이미지와 텍스트 중 더 큰 개수를 반환
        return maxOf(images.size, texts.size)
    }
}
