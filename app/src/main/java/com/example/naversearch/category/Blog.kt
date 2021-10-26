package com.example.naversearch.category

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naversearch.databinding.FrgBlogBinding
import com.example.naversearch.model.NaverViewModel


@SuppressLint("ResourceType")
class Blog : Fragment() {
    private lateinit var binding: FrgBlogBinding
    val type = "blog"
    val category = "blog"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FrgBlogBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@Blog
            model = NaverViewModel()
        }
        return binding.root
    }
}



