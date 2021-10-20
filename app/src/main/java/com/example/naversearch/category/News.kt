package com.example.naversearch.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naversearch.databinding.FrgNewsBinding
import com.example.naversearch.model.TextModel

class News : Fragment() {
    private lateinit var binding: FrgNewsBinding
    val model = TextModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgNewsBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@News
        }
        return binding.root
    }

    fun result(v: View) {
        when (v) {
            binding.btnNews -> {
                val keyword = binding.etNews.text.toString()
                model.search("news", "news", keyword, requireContext(), binding.rvNews)
            }

            binding.btnNewsGet -> {
                model.lookUp("news", requireContext(), binding.rvNews)
            }
        }
    }

}