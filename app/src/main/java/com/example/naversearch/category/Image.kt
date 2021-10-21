package com.example.naversearch.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naversearch.databinding.FrgImageBinding
import com.example.naversearch.model.Model

class Image : Fragment() {

    lateinit var binding: FrgImageBinding
    val type = "image"
    val category = "image"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgImageBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@Image
            model = Model()
        }

        return binding.root
    }
}