package com.example.naversearch.model

import android.content.Context
import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.naversearch.ResultGetSearch
import com.example.naversearch.adapter.ImageAdapter
import com.example.naversearch.adapter.TextAdapter
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Model {
    private val textAdapter = TextAdapter()
    private val imageAdapter = ImageAdapter()
    private val sd = mutableListOf<SearchData>()
    val reqArray = JSONArray()

    fun search(
        type: String,
        category: String,
        keyword: String,
        context: Context,
        rv: RecyclerView
    ) {

        //쉐어드 프리퍼런스
        val sharedPreferences =
            context.getSharedPreferences(type, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        sd.clear()
        sharedPreferences.getString(type, "")
        val retrofit = Retrofit.Builder()
            .baseUrl(CommonVariable.BASE_URL_NAVER_API)
            .addConverterFactory(GsonConverterFactory.create()) //Gson 변환기 사용
            .build()
        val api = retrofit.create(NaverAPI::class.java)

        //예외처리 (아무것도 입력하지 않고 검색했을 경우)
        if (keyword != "") {
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
                                HtmlCompat.fromHtml(
                                    item.title,
                                    HtmlCompat.FROM_HTML_MODE_LEGACY
                                )
                                    .toString(),
                                HtmlCompat.fromHtml(
                                    item.description,
                                    HtmlCompat.FROM_HTML_MODE_LEGACY
                                ).toString(),

                                HtmlCompat.fromHtml(
                                    item.thumbnail,
                                    HtmlCompat.FROM_HTML_MODE_LEGACY
                                )
                                    .toString(),
                            )
                        )

                        //Json 형식으로 저장
                        val jsonObject = JSONObject()
                        jsonObject.put(
                            "title",
                            HtmlCompat.fromHtml(
                                item.title,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            )
                        )
                        jsonObject.put(
                            "description",
                            HtmlCompat.fromHtml(
                                item.description,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            )
                        )
                        jsonObject.put(
                            "thumbnail",
                            HtmlCompat.fromHtml(
                                item.thumbnail,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            )
                        )
                        jsonObject.put(
                            "link",
                            HtmlCompat.fromHtml(item.link, HtmlCompat.FROM_HTML_MODE_LEGACY)
                        )
                        reqArray.put(jsonObject)
                    }
                    editor.putString(type, reqArray.toString())
                    editor.apply()

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

    fun lookUp(type: String, context: Context, rv: RecyclerView) {
        val sharedPreferences =
            context.getSharedPreferences(type, Context.MODE_PRIVATE)

        sd.clear()
        sharedPreferences.getString(type, "")

        //shared prefrence 불러와서 json 파싱하기
        val jsonArray =
            JSONTokener(sharedPreferences.getString(type, null)).nextValue() as JSONArray
        for (i in 0 until jsonArray.length()) {
            if (type == "image") {
                val thumbnail = jsonArray.getJSONObject(i).getString("thumbnail")
                sd.add(SearchData("", "", thumbnail, ""))
                imageAdapter.submitList(sd)
                rv.adapter = imageAdapter

            } else {
                val title = jsonArray.getJSONObject(i).getString("title")
                val description = jsonArray.getJSONObject(i).getString("description")
                sd.add(SearchData(title, description, "", ""))
                textAdapter.submitList(sd)
                rv.adapter = textAdapter
            }
        }

    }
}