package com.example.naversearch.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naversearch.databinding.FrgBlogBinding
import com.example.naversearch.model.NaverModel


@SuppressLint("ResourceType")
class Blog : Fragment() {
    private lateinit var binding: FrgBlogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FrgBlogBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@Blog
            naverModel = NaverModel(requireActivity().application, BLOG_TYPE)
        }
        return binding.root
    }

    companion object {
        const val BLOG_TYPE = "blog"
        const val BLOG_CATEGORY = "blog"
    }
}



