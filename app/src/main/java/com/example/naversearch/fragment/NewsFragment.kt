package com.example.naversearch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naversearch.databinding.FrgNewsBinding
import com.example.naversearch.model.NaverModel

class NewsFragment : Fragment() {

    private lateinit var binding: FrgNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgNewsBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@NewsFragment
            naverModel = NaverModel(requireActivity().application, NEWS_TYPE)
        }
        return binding.root
    }

    companion object {
        const val NEWS_TYPE = "news"
        const val NEWS_CATEGORY = "news"
    }
}