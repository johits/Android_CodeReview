package com.example.naversearch.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.naversearch.model.NaverModel
import com.example.naversearch.model.SearchData

class CafeFragmentViewModel : ViewModel() {
    private val naverModel = NaverModel("blog", "blog")
    private val searchDataModel: LiveData<List<SearchData>>
        get() = naverModel._searchDataModel

    fun resultBlogSearch(keyword: String) {
        naverModel.search(keyword)
    }

    fun getAll(): LiveData<List<SearchData>> {
        return searchDataModel
    }
}