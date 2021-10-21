package com.example.naversearch.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naversearch.databinding.FrgNewsBinding
import com.example.naversearch.model.Model

class News : Fragment() {
    private lateinit var binding: FrgNewsBinding
    val type = "news"
    val category = "news"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgNewsBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@News
            model = Model()
        }
        return binding.root
    }
}