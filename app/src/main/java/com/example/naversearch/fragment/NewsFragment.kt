package com.example.naversearch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.naversearch.adapter.TextAdapter
import com.example.naversearch.databinding.FrgNewsBinding
import com.example.naversearch.model.NaverModel

class NewsFragment : Fragment() {

    private lateinit var binding: FrgNewsBinding
    private val newsFragmentViewModel: NewsFragmentViewModel by lazy {
        ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                NewsFragmentViewModel() as T
        }).get(NewsFragmentViewModel::class.java)
    }
    private val textAdapter = TextAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgNewsBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@NewsFragment
            rvNews.adapter = textAdapter
            btnNews.setOnClickListener {newsFragmentViewModel.resultBlogSearch(etNews.text.toString())}
            newsFragmentViewModel.getAll().observe(requireActivity()){
                textAdapter.submitList(it)
            }
        }
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        with(binding){
            rvNews.adapter = textAdapter
        }
    }
}