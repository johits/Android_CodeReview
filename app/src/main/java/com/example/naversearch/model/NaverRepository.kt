package com.example.naversearch.model

import android.content.SharedPreferences
import android.os.SystemClock
import androidx.core.content.edit
import com.example.naversearch.ResultGetSeachItem
import com.example.naversearch.ResultGetSearch
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NaverRepository(
    private val type: String,
    private val category: String,
    private val sharedPreferences: SharedPreferences
) {

    private var lastClickTime = 0L
    private var gson = Gson()
    private var json = ""

    fun search(
        keyword: String,
        onSuccess: (List<SearchData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) { //1초 이내 중복 클릭 방지
        if (keyword.isEmpty()) {
            onFailure(Throwable("검색어가 없습니다."))
        }

        val searchDataList = mutableListOf<SearchData>()
        val reqArray = JSONArray()

        if (SystemClock.elapsedRealtime() - lastClickTime > 1000) {

            getNaverApi()
                .getSearch(category, keyword)
                .enqueue(object : Callback<ResultGetSearch> {
                    override fun onResponse(
                        call: Call<ResultGetSearch>,
                        response: Response<ResultGetSearch>,
                    ) {
                        if (!response.isSuccessful) {
                            onFailure(Throwable("통신이 성공하지 않았습니다."))
                        }

                        response.body()?.resultGetSearchItem
                            ?.map { item -> toSearchData(item) }
                            ?.forEach { item ->
                                searchDataList.add(item)
                                reqArray.put(gson.toJson(item))
                            }

                        sharedPreferences.edit(commit = true) {
                            putString(type, reqArray.toString())
                        }

                        onSuccess(searchDataList)
                    }

                    override fun onFailure(call: Call<ResultGetSearch>, t: Throwable) {
                        onFailure(t)
                    }
                })
        }
        lastClickTime = SystemClock.elapsedRealtime()
    }

    private fun getNaverApi(): NaverAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(CommonVariable.BASE_URL_NAVER_API)
            .addConverterFactory(GsonConverterFactory.create()) //Gson 변환기 사용
            .build()
        return retrofit.create(NaverAPI::class.java)
    }

    fun lookUp(): List<SearchData> {
        val searchDataList = mutableListOf<SearchData>()
        searchDataList.clear()
        sharedPreferences.getString(type, "")
        val jsonArray =
            JSONTokener(sharedPreferences.getString(type, null)).nextValue() as JSONArray
        for (i in 0 until jsonArray.length()) {
            return if (type == DIFF_TYPE) {
                searchDataList.add(gson.fromJson(jsonArray[i].toString(), SearchData::class.java))
                //                imageAdapter.submitList(sd)
                //                rv.adapter = imageAdapter
                searchDataList
            } else {
                searchDataList.add(gson.fromJson(jsonArray[i].toString(), SearchData::class.java))
                //                textAdapter.submitList(sd)
                //                rv.adapter = textAdapter
                searchDataList
            }
        }
    }

    fun toSearchData(resultGetSeachItem: ResultGetSeachItem): SearchData {
        return SearchData(
            resultGetSeachItem.title,
            resultGetSeachItem.description,
            resultGetSeachItem.thumbnail,
            resultGetSeachItem.link
        )
    }

    companion object {
        const val DIFF_TYPE = "image"
    }
}