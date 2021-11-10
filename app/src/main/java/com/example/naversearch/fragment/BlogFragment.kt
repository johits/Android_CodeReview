package com.example.naversearch.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.naversearch.adapter.TextAdapter
import com.example.naversearch.databinding.FrgBlogBinding
import com.example.naversearch.model.NaverModel


@SuppressLint("ResourceType")
class BlogFragment : Fragment() {
    private lateinit var binding: FrgBlogBinding
    private val blogFragmentViewModel: BlogFragmentViewModel by lazy {
        ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                BlogFragmentViewModel() as T
        }).get(BlogFragmentViewModel::class.java)
    }

    private val textAdapter = TextAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FrgBlogBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@BlogFragment
            rvBlog.adapter = textAdapter
            btnBlog.setOnClickListener {blogFragmentViewModel.resultBlogSearch(etBlog.text.toString())}
            blogFragmentViewModel.getAll().observe(requireActivity()){
                textAdapter.submitList(it)
            }
        }
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        with(binding){
            rvBlog.adapter = textAdapter
        }
    }

//    companion object {
//        const val BLOG_TYPE = "blog"
//        const val BLOG_CATEGORY = "blog"
//    }
}



