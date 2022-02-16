package com.example.naversearch.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.naversearch.model.NaverRepository
import com.example.naversearch.model.SearchData

class ImageFragmentViewModel(private val naverRepository: NaverRepository) : ViewModel() {
//    private val naverModel = NaverRepository("image", "image", shared)
//    private val searchDataModel: LiveData<List<SearchData>>
//        get() = naverModel._searchDataModel

    private val _searchList = MutableLiveData<List<SearchData>>()
    val searchList: LiveData<List<SearchData>> = _searchList


    fun resultBlogSearch(keyword: String) {
        naverRepository.search(
            keyword,
            onSuccess = {
                _searchList.value = it
            },
            onFailure = {

            })
    }

    fun sample(callback: (Boolean) -> Unit) {
        Thread.sleep(3000L)
        callback(true)
    }

    fun resultLookUpBlogSearch() {
        naverRepository.lookUp()
    }

//
//    fun resultBlogSearch(keyword: String) {
//        naverModel.search(keyword)
//    }
//
//    fun resultLookUpImageSearch() {
//        naverModel.lookUp()
//    }
//
//    fun getAll(): LiveData<List<SearchData>> {
//        return searchDataModel
//    }
}