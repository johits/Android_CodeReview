package com.example.naversearch.fragment

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.naversearch.model.NaverModel
import com.example.naversearch.model.SearchData

//ViewModel은 Android에 대해서 아무것도 알지 못해야 함

class BlogFragmentViewModel(shared: SharedPreferences) : ViewModel() {
    private val naverModel = NaverModel("blog", "blog", shared)

    private val searchDataModel: LiveData<List<SearchData>>
        get() = naverModel._searchDataModel

    fun resultBlogSearch(keyword: String) {
        naverModel.search(keyword)
    }

    fun resultLookUpBlogSearch() {
        naverModel.lookUp()
    }

    fun getAll(): LiveData<List<SearchData>> {
        return searchDataModel
    }
}