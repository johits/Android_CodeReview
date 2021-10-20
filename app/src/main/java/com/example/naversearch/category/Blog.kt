package com.example.naversearch.category

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naversearch.databinding.FrgBlogBinding
import com.example.naversearch.model.TextModel


@SuppressLint("ResourceType")
class Blog : Fragment() {
    private lateinit var binding: FrgBlogBinding
    val model = TextModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FrgBlogBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@Blog
        }
        return binding.root
    }


    fun result(v: View) {
        when (v) {
            binding.btnBlog -> {
                val keyword = binding.etBlog.text.toString()
                model.search("blog", "blog", keyword, requireContext(), binding.rv)
            }

            binding.btnBlogGet -> {
                model.lookUp("blog", requireContext(), binding.rv)
            }
        }
    }
}



