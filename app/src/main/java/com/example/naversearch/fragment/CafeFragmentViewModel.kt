package com.example.naversearch.fragment

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.naversearch.model.NaverModel
import com.example.naversearch.model.SearchData

class CafeFragmentViewModel(shared: SharedPreferences) : ViewModel() {
    private val naverModel = NaverModel("cafe", "cafearticle", shared)
    private val searchDataModel: LiveData<List<SearchData>>
        get() = naverModel._searchDataModel

    fun resultBlogSearch(keyword: String) {
        naverModel.search(keyword)
    }

    fun resultLookUpCafeSearch() {
        naverModel.lookUp()
    }

    fun getAll(): LiveData<List<SearchData>> {
        return searchDataModel
    }
}