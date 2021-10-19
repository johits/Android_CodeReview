package com.example.naversearch.category

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.example.naversearch.ResultGetSearch
import com.example.naversearch.adapter.TextAdapter
import com.example.naversearch.databinding.FrgNewsBinding
import com.example.naversearch.model.NaverAPI
import com.example.naversearch.model.SearchData
import com.example.naversearch.model.CommonVariable
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class News : Fragment() {
    lateinit var binding: FrgNewsBinding
    private val textAdapter = TextAdapter()
    private val sd = mutableListOf<SearchData>()
    val reqArray = JSONArray()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgNewsBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@News
            rvNews.adapter = textAdapter
        }

        return binding.root
    }

    fun result(v: View) {

        val sharedPreferences = requireContext().getSharedPreferences("news", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        sd.clear()

        when (v) {
            binding.btnNews -> {
                val keyword = binding.etNews.text.toString()
                Log.d("키워드", "키워드 $keyword")
                val retrofit = Retrofit.Builder()
                    .baseUrl(CommonVariable.BASE_URL_NAVER_API)
                    .addConverterFactory(GsonConverterFactory.create()) //Gson 변환기 사용
                    .build()
                val api = retrofit.create(NaverAPI::class.java)

                //예외처리 (아무것도 입력하지 않고 검색했을 경우)
                if (!keyword.equals("")) {
                    val callGetSearchNews = api.getSearchNews("news", keyword)
                    callGetSearchNews.enqueue(object : Callback<ResultGetSearch> {
                        override fun onResponse(
                            call: Call<ResultGetSearch>,
                            response: Response<ResultGetSearch>
                        ) {

                            for (item in response.body()?.items!!) {

                                //검색 결과 데이터(제목, 내용) 추가하기
                                sd.add(
                                    SearchData(
                                        HtmlCompat.fromHtml(
                                            item.title,
                                            HtmlCompat.FROM_HTML_MODE_LEGACY
                                        ).toString(),
                                        HtmlCompat.fromHtml(
                                            item.description,
                                            HtmlCompat.FROM_HTML_MODE_LEGACY
                                        ).toString()
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
                            textAdapter.addItem(sd)
                            editor.putString("news", reqArray.toString())
                            editor.apply()
                        }


                        override fun onFailure(call: Call<ResultGetSearch>, t: Throwable) {
                            Log.d("MainActivity", "실패 : $t")
                        }
                    })
                } else {
                    sd.clear()
                    textAdapter.addItem(sd)
                }
            }

            binding.btnNewsGet -> {
                sharedPreferences.getString("news", "")

                //shared prefrence 불러와서 json 파싱하기
                val jsonArray =
                    JSONTokener(sharedPreferences.getString("news", null)).nextValue() as JSONArray
                for (i in 0 until jsonArray.length()) {
                    val title = jsonArray.getJSONObject(i).getString("title")
                    val description = jsonArray.getJSONObject(i).getString("description")
                    sd.add(SearchData(title, description, "", ""))
                    textAdapter.addItem(sd)
                }

            }
        }

    }

}