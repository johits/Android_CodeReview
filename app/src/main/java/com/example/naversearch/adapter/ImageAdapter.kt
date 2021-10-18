package com.example.naversearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.naversearch.model.SearchData
import com.example.naversearch.databinding.RvImageBinding

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private val datas = mutableListOf<SearchData>()

    // 뷰 홀더와 레이아웃 파일을 연결해주는 역할
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size


    // 뷰 홀더에 데이터를 연결해주는 함수
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }


    fun addItem(item: List<SearchData>) {
        datas.clear()
        datas.addAll(item)
        notifyDataSetChanged()
    }

    // 뷰 홀더에 들어갈 데이터 형식 정의
    inner class ViewHolder(private val binding: RvImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchData) {
            Glide.with(binding.root) //context 접근을 제공
                .load(item.thumbnail)
                .override(150, 150) // ex) override(600, 200)
                .into(binding.ivIamge)
        }
    }
}


