package com.example.naversearch.fragment

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.naversearch.adapter.TextAdapter
import com.example.naversearch.databinding.FrgBlogBinding
import com.example.naversearch.model.NaverRepository
import com.example.naversearch.ui.MainActivity


@SuppressLint("ResourceType")
class BlogFragment : Fragment() {
    private lateinit var binding: FrgBlogBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val blogFragmentViewModel: BlogFragmentViewModel by lazy {
        ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                BlogFragmentViewModel(NaverRepository("blog", "blog", sharedPreferences)) as T
        }).get(BlogFragmentViewModel::class.java)
    }
    private val textAdapter = TextAdapter()
    private lateinit var listener: MainActivity.BtnListener
    var callback: ((Boolean) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgBlogBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("blog", MODE_PRIVATE)
        binding.apply {
            fragment = this@BlogFragment
            blogFragmentViewModel.getAll().observe(requireActivity()) {
                textAdapter.submitList(it?.toMutableList())
            }
        }
        setRecyclerView()
        observeCallback()
        return binding.root
    }

    private fun observeCallback() {
        blogFragmentViewModel.sample {
            // callback이 호출될 때 여기를 탐
        }
    }

    private fun setRecyclerView() {
        with(binding) {
            rvBlog.adapter = textAdapter
        }
    }

    inner class OnBtnListener : MainActivity.BtnListener {
        override fun onClickSearch(keword: String) {
            blogFragmentViewModel.resultBlogSearch(keword)
        }

        override fun onClickLookUp() {
            blogFragmentViewModel.resultLookUpBlogSearch()
        }

    }
}



