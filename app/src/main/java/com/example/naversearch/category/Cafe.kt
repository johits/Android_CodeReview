package com.example.naversearch.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naversearch.databinding.FrgCafeBinding
import com.example.naversearch.model.NaverViewModel

class Cafe : Fragment() {

    private lateinit var binding: FrgCafeBinding
    val type = "cafe"
    val category = "cafearticle"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FrgCafeBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@Cafe
            model = NaverViewModel()
        }
        return binding.root
    }
}