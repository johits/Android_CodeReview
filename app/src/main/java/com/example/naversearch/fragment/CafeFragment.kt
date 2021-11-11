package com.example.naversearch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.naversearch.adapter.TextAdapter
import com.example.naversearch.databinding.FrgCafeBinding

class CafeFragment : Fragment() {

    private lateinit var binding: FrgCafeBinding
    private val cafeFragmentViewModel: CafeFragmentViewModel by lazy {
        ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                CafeFragmentViewModel() as T
        }).get(CafeFragmentViewModel::class.java)
    }
    private val textAdapter = TextAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FrgCafeBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@CafeFragment
            btnCafe.setOnClickListener { cafeFragmentViewModel.resultBlogSearch(etCafe.text.toString()) }
            cafeFragmentViewModel.getAll().observe(requireActivity()) {
                textAdapter.submitList(it?.toMutableList())
            }
        }
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        with(binding) {
            rvCafe.adapter = textAdapter
        }
    }
}