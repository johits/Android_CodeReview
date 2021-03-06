package com.example.naversearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.naversearch.callback.DiffUtilCallback
import com.example.naversearch.databinding.RvTextBinding
import com.example.naversearch.model.SearchData
import com.example.naversearch.viewholder.TextViewHolder


class TextAdapter : ListAdapter<SearchData, TextViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val binding = RvTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}