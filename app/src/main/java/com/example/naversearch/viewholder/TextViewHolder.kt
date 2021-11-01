package com.example.naversearch.viewholder

import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.naversearch.databinding.RvTextBinding
import com.example.naversearch.model.SearchData

class TextViewHolder(private val binding: RvTextBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SearchData) {
        binding.apply {
            tvTitle.text = HtmlCompat.fromHtml(
                item.title,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
                .toString()

            tvContents.text = HtmlCompat.fromHtml(
                item.description,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
                .toString()
        }
    }
}