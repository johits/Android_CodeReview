package com.example.naversearch.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.naversearch.databinding.RvImageBinding
import com.example.naversearch.model.SearchData

class ImageViewHolder(private val binding: RvImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SearchData) {
        Glide.with(binding.root) //context 접근을 제공
            .load(item.thumbnail)
            .override(150, 150) // ex) override(600, 200)
            .into(binding.ivIamge)
    }
}