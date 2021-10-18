package com.example.naversearch.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.naversearch.model.SearchData
import com.example.naversearch.databinding.RvTextBinding


class TextAdapter : RecyclerView.Adapter<TextAdapter.ViewHolder>() {

    private val datas = mutableListOf<SearchData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: MutableList<SearchData>) {
        datas.clear()
        datas.addAll(item)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RvTextBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchData) { //데이터 넣기
            binding.tvTitle.text = item.title
            binding.tvContents.text = item.description
        }
    }


}