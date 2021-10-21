package com.example.naversearch.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naversearch.databinding.FrgCafeBinding
import com.example.naversearch.model.Model

class Cafe : Fragment() {

    private lateinit var binding: FrgCafeBinding
    val model = Model()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FrgCafeBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@Cafe
        }
        return binding.root
    }

    fun result(v: View) {
        when (v) {
            binding.btnCafe -> {
                val keyword = binding.etCafe.text.toString()
                model.search("cafe", "cafearticle", keyword, requireContext(), binding.rvCafe)
                binding.etCafe.setText("")
            }

            binding.btnCafeGet -> {
                model.lookUp("cafe", requireContext(), binding.rvCafe)
            }
        }
    }
}