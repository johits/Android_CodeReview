package com.example.naversearch.fragment

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.naversearch.model.NaverRepository
import com.example.naversearch.model.SearchData

class NewsFragmentViewModel(shared: SharedPreferences) : ViewModel() {
    private val naverModel = NaverRepository("news", "news", shared)
    private val searchDataModel: LiveData<List<SearchData>>
        get() = naverModel._searchDataModel

    fun resultBlogSearch(keyword: String) {
        naverModel.search(keyword)
    }

    fun resultLookUpNewsSearch() {
        naverModel.lookUp()
    }

    fun getAll(): LiveData<List<SearchData>> {
        return searchDataModel
    }
}