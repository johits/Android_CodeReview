package com.example.naversearch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.naversearch.adapter.ImageAdapter
import com.example.naversearch.databinding.FrgImageBinding
import com.example.naversearch.model.NaverModel

class ImageFragment : Fragment() {

    lateinit var binding: FrgImageBinding
    private val imageFragmentViewModel: ImageFragmentViewModel by lazy {
        ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                ImageFragmentViewModel() as T
        }).get(ImageFragmentViewModel::class.java)
    }
    private val imageAdapter = ImageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgImageBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@ImageFragment
            btnImage.setOnClickListener {imageFragmentViewModel.resultBlogSearch(etImage.text.toString())}
            imageFragmentViewModel.getAll().observe(requireActivity()){
                imageAdapter.submitList(it?.toMutableList())
            }
        }
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        with(binding){
            rvImage.adapter = imageAdapter
        }
    }
}