package com.example.naversearch.callback

import androidx.recyclerview.widget.DiffUtil
import com.example.naversearch.model.SearchData

class DiffUtilCallback : DiffUtil.ItemCallback<SearchData>() {

    override fun areItemsTheSame(oldItem: SearchData, newItem: SearchData) =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: SearchData, newItem: SearchData) =
        oldItem.title == newItem.title
}