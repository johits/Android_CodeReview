package com.example.naversearch.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.naversearch.databinding.RvTextBinding
import com.example.naversearch.model.SearchData

class TextViewHolder(private val binding: RvTextBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SearchData) {
        binding.apply {
           tvTitle.text = item.title
           tvContents.text = item.description
        }
    }
}