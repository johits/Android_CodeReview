package com.example.naversearch.category

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naversearch.databinding.FrgImageBinding
import com.example.naversearch.model.NaverModel

class Image : Fragment() {

    lateinit var binding: FrgImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgImageBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@Image
            naverModel = NaverModel(requireActivity().application)
        }

        return binding.root
    }

    companion object {
        const val IMAGE_TYPE = "image"
        const val IMAGE_CATEGORY = "image"
    }
}