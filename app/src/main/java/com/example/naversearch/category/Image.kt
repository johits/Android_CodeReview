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
    val model = Model()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgImageBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@Image
        }

        return binding.root
    }

    fun result(v: View) {
        when (v) {
            binding.btnImage -> {
                val keyword = binding.etImage.text.toString()
                model.search("image", "image", keyword, requireContext(), binding.rvImage)
                binding.etImage.setText("")
            }

            binding.btnImageGet -> {
                model.lookUp("image", requireContext(), binding.rvImage)
            }
        }
    }
}