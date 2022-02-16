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
import com.example.naversearch.adapter.ImageAdapter
import com.example.naversearch.databinding.FrgImageBinding
import com.example.naversearch.model.NaverRepository
import com.example.naversearch.ui.MainActivity

class ImageFragment : Fragment() {

    lateinit var binding: FrgImageBinding
    lateinit var sharedPreferences: SharedPreferences
    private val imageFragmentViewModel: ImageFragmentViewModel by lazy {
        ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                ImageFragmentViewModel(NaverRepository("image", "image", sharedPreferences)) as T
        }).get(ImageFragmentViewModel::class.java)
    }
    private val imageAdapter = ImageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgImageBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("image", Context.MODE_PRIVATE)
        binding.apply {
            fragment = this@ImageFragment
            btnImage.setOnClickListener { imageFragmentViewModel.resultBlogSearch(etImage.text.toString()) }
//            btnImageGet.setOnClickListener { imageFragmentViewModel.resultLookUpImageSearch() }
            imageFragmentViewModel.searchList.observe(requireActivity()) {
                imageAdapter.submitList(it)
            }
        }
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        with(binding) {
            rvImage.adapter = imageAdapter
        }
    }

    inner class OnBtnListener : MainActivity.BtnListener {
        override fun onClickSearch(keword: String) {
            imageFragmentViewModel.resultBlogSearch(keword)
        }

        override fun onClickLookUp() {
            imageFragmentViewModel.resultLookUpBlogSearch()
        }

    }
}