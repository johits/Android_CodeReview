package com.example.naversearch.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.naversearch.adapter.TextAdapter
import com.example.naversearch.databinding.FrgNewsBinding

class NewsFragment : Fragment() {

    private lateinit var binding: FrgNewsBinding
    lateinit var sharedPreferences: SharedPreferences
    private val newsFragmentViewModel: NewsFragmentViewModel by lazy {
        ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                NewsFragmentViewModel(sharedPreferences) as T
        }).get(NewsFragmentViewModel::class.java)
    }
    private val textAdapter = TextAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgNewsBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("news", Context.MODE_PRIVATE)
        binding.apply {
            fragment = this@NewsFragment
            btnNews.setOnClickListener { newsFragmentViewModel.resultBlogSearch(etNews.text.toString()) }
            btnNewsGet.setOnClickListener { newsFragmentViewModel.resultLookUpNewsSearch() }
            newsFragmentViewModel.getAll().observe(requireActivity()) {
                textAdapter.submitList(it?.toMutableList())
            }
        }
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        with(binding) {
            rvNews.adapter = textAdapter
        }
    }
}