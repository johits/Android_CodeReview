package com.example.naversearch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naversearch.databinding.FrgCafeBinding
import com.example.naversearch.model.NaverModel

class Cafe : Fragment() {

    private lateinit var binding: FrgCafeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FrgCafeBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@Cafe
            naverModel = NaverModel(requireActivity().application, CAFE_TYPE)
        }
        return binding.root
    }

    companion object {
        const val CAFE_TYPE = "cafe"
        const val CAFE_CATEGORY = "cafearticle"
    }
}