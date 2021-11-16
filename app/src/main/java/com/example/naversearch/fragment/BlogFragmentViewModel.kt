package com.example.naversearch.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.naversearch.model.NaverRepository
import com.example.naversearch.model.SearchData

//ViewModel은 Android에 대해서 아무것도 알지 못해야 함

class BlogFragmentViewModel(
    private val naverRepository: NaverRepository
) : ViewModel() {

    /**
     * 1. 빨간줄 없애기
     * 2. LiveData로 Fragment로 보내기
     * 3. activity에서 livedata를 set하기
     * 3. fragment에서는 activityViewModel을 이용해서 activity의 livedata를 참조하며 관찰
     * 4. search 이벤트 때 observe가 가능
     * 5. fragment에 viewmodel 메서드를 호출
     *
     * 1. 빨간줄 없애기
     * 2. LiveData로 Fragment로 보내기
     * 3. activity에서 callback : () -> Unit 이런 형태의 콜백을 호출
     * 3. fragment에서는 activityCallback을 참조하여 정의함
     * 4. search 이벤트 때 observe가 가능
     * 5. fragment에 viewmodel 메서드를 호출
     */

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
}