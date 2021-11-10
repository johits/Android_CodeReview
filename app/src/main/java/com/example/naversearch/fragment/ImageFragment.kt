package com.example.naversearch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naversearch.databinding.FrgImageBinding
import com.example.naversearch.model.NaverModel

class ImageFragment : Fragment() {

    lateinit var binding: FrgImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgImageBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@ImageFragment
            naverModel = NaverModel(requireActivity().application, IMAGE_TYPE)
        }

        return binding.root
    }

    companion object {
        const val IMAGE_TYPE = "image"
        const val IMAGE_CATEGORY = "image"
    }
}