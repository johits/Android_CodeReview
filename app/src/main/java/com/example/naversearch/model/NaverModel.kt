package com.example.naversearch.model

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.SystemClock
import android.provider.Settings.Global.putString
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.naversearch.ResultGetSearch
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NaverModel(val type: String, private val category: String, val sharedPreferences: SharedPreferences) {
    @SuppressLint("StaticFieldLeak")
    private val sd = mutableListOf<SearchData>()
    val reqArray = JSONArray()
    private var mLastClickTime = 0L
    var gson = Gson()
    var json = ""
    var _searchDataModel = MutableLiveData<List<SearchData>>() // MutableLiveData 객체 생성

    fun search(
        keyword: String
    ) { //1초 이내 중복 클릭 방지
        if (SystemClock.elapsedRealtime() - mLastClickTime > 1000) {

            sd.clear()
            val retrofit = Retrofit.Builder()
                .baseUrl(CommonVariable.BASE_URL_NAVER_API)
                .addConverterFactory(GsonConverterFactory.create()) //Gson 변환기 사용
                .build()
            val api = retrofit.create(NaverAPI::class.java)

            //예외처리 (아무것도 입력하지 않고 검색했을 경우)
            if (keyword.isNotEmpty()) {
                val callGetSearch = api.getSearch(category, keyword)
                callGetSearch.enqueue(object : Callback<ResultGetSearch> {
                    override fun onResponse(
                        call: Call<ResultGetSearch>,
                        response: Response<ResultGetSearch>,
                    ) {

                        for (item in response.body()?.items!!) {
                            //검색 결과 데이터(제목, 내용) 추가하기
                            sd.add(
                                SearchData(
                                    item.title,
                                    item.description,
                                    item.thumbnail,
                                    item.link
                                )
                            )

                            json = gson.toJson(
                                SearchData(
                                    item.title,
                                    item.description,
                                    item.thumbnail,
                                    item.link
                                )
                            )
                            reqArray.put(json)
                        }

                        sharedPreferences.edit(commit = true) {
                            putString(type, reqArray.toString())
                        }

                        _searchDataModel.value = sd
                    }

                    override fun onFailure(call: Call<ResultGetSearch>, t: Throwable) {
                    }

                })
            } else {
                sd.clear()
                _searchDataModel.value = sd

            }
        }
        mLastClickTime = SystemClock.elapsedRealtime()
    }

    fun lookUp() {
        sd.clear()
        sharedPreferences.getString(type, "")
        val jsonArray =
            JSONTokener(sharedPreferences.getString(type, null)).nextValue() as JSONArray
        for (i in 0 until jsonArray.length()) {
            if (type == DIFF_TYPE) {
                sd.add(gson.fromJson(jsonArray[i].toString(), SearchData::class.java))
//                imageAdapter.submitList(sd)
//                rv.adapter = imageAdapter
                _searchDataModel.value = sd
            } else {
                sd.add(gson.fromJson(jsonArray[i].toString(), SearchData::class.java))
//                textAdapter.submitList(sd)
//                rv.adapter = textAdapter
                _searchDataModel.value = sd
            }
        }

    }

    companion object {
        const val DIFF_TYPE = "image"
    }
}