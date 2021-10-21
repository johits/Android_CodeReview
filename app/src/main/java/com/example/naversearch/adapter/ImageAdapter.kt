package com.example.naversearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.naversearch.callback.DiffUtilCallback
import com.example.naversearch.databinding.RvImageBinding
import com.example.naversearch.model.SearchData
import com.example.naversearch.viewholder.ImageViewHolder

class ImageAdapter : ListAdapter<SearchData, ImageViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = RvImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<SearchData>?) {
        super.submitList(list)
    }
}