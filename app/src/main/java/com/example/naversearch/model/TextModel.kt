package com.example.naversearch.model

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.naversearch.adapter.TextAdapter
import com.example.naversearch.databinding.FrgCafeBinding
import org.json.JSONArray
import org.json.JSONTokener

open class TextModel<B : ViewDataBinding>(
    @LayoutRes val layoutRes: Int) :Fragment() {
   lateinit var binding: B
    private val textAdapter = TextAdapter()
    private val sd = mutableListOf<SearchData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.setContentView(requireActivity(), layoutRes)
        binding.lifecycleOwner = this
//        val binding = FrgCafeBinding.inflate(inflater, container, false)
//        binding.rvCafe.adapter = textAdapter
        Log.e("ㅌㅅㅌ", "onCreateView: 들어옴", )
        return binding.root
    }

    fun read() {

        val sharedPreferences =
            requireContext().getSharedPreferences("cafe", Context.MODE_PRIVATE)

        sd.clear()
        sharedPreferences.getString("cafe", "")

        //shared prefrence 불러와서 json 파싱하기
        val jsonArray =
            JSONTokener(sharedPreferences.getString("cafe", null)).nextValue() as JSONArray
        for (i in 0 until jsonArray.length()) {
            val title = jsonArray.getJSONObject(i).getString("title")
            val description = jsonArray.getJSONObject(i).getString("description")
            sd.add(SearchData(title, description, "", ""))
        }
        textAdapter.addItem(sd)
        Log.e("ㅌㅅㅌ", "read: sd가 뭔지 보자: $sd", )
    }
}