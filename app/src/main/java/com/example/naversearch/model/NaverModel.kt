package com.example.naversearch.model

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.os.SystemClock
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.naversearch.ResultGetSearch
import com.example.naversearch.adapter.ImageAdapter
import com.example.naversearch.adapter.TextAdapter
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NaverModel(application: Application, type: String) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val textAdapter = TextAdapter()
    private val imageAdapter = ImageAdapter()
    private val sd = mutableListOf<SearchData>()
    val reqArray = JSONArray()
    private var mLastClickTime = 0L
    var gson = Gson()
    var json = ""
    val type = type
    private val sharedPreferences =
        context.getSharedPreferences(type, MODE_PRIVATE)

    fun search(
        category: String,
        keyword: String,
        rv: RecyclerView
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


                        if (type == "image") {
                            imageAdapter.submitList(sd)
                            rv.adapter = imageAdapter
                        } else {
                            textAdapter.submitList(sd)
                            rv.adapter = textAdapter
                        }
                    }

                    override fun onFailure(call: Call<ResultGetSearch>, t: Throwable) {
                    }

                })
            } else {
                sd.clear()
                if (type == "image") {
                    imageAdapter.submitList(sd)
                    rv.adapter = imageAdapter
                } else {
                    textAdapter.submitList(sd)
                    rv.adapter = textAdapter
                }
            }
        }
        mLastClickTime = SystemClock.elapsedRealtime()
    }

    fun lookUp(rv: RecyclerView) {
        sd.clear()
        sharedPreferences.getString(type, "")


        val jsonArray =
            JSONTokener(sharedPreferences.getString(type, null)).nextValue() as JSONArray
        for (i in 0 until jsonArray.length()) {
            if (type == DIFF_TYPE) {
                sd.add(gson.fromJson(jsonArray[i].toString(), SearchData::class.java))
                imageAdapter.submitList(sd)
                rv.adapter = imageAdapter

            } else {
                sd.add(gson.fromJson(jsonArray[i].toString(), SearchData::class.java))
                textAdapter.submitList(sd)
                rv.adapter = textAdapter
            }
        }

    }

    companion object {
        const val DIFF_TYPE = "image"
    }
}