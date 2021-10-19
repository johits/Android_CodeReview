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
import com.example.naversearch.adapter.ImageAdapter
import com.example.naversearch.databinding.FrgImageBinding
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

class Image : Fragment() {

    lateinit var binding: FrgImageBinding
    private val imageAdapter = ImageAdapter()
    private val sd = mutableListOf<SearchData>()
    val reqArray = JSONArray()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrgImageBinding.inflate(inflater, container, false)

        binding.apply {
            fragment = this@Image
            rvImage.adapter = imageAdapter
        }

        return binding.root
    }

    fun result(v: View) {

        val sharedPreferences = requireContext().getSharedPreferences("image", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        sd.clear()

        when (v) {
            binding.btnImage -> {
                val keyword = binding.etImage.text.toString()
                val retrofit = Retrofit.Builder()
                    .baseUrl(CommonVariable.BASE_URL_NAVER_API)
                    .addConverterFactory(GsonConverterFactory.create()) //Gson 변환기 사용
                    .build()
                val api = retrofit.create(NaverAPI::class.java)

                //예외처리 (아무것도 입력하지 않고 검색했을 경우)
                if (!keyword.equals("")) {
                    val callGetSearchNews = api.getSearchNews("image", keyword)
                    callGetSearchNews.enqueue(object : Callback<ResultGetSearch> {
                        override fun onResponse(
                            call: Call<ResultGetSearch>,
                            response: Response<ResultGetSearch>
                        ) {

                            Log.d("데이터", "현재 저장된 데이터는 $sd")
                            for (item in response.body()?.items!!) { //코틀린에서 !! 안 쓰는 게 좋음 null이 아닌 걸 보장 (null이 오면 터짐)
                                Log.e("이미지결과", "onResponse: ${response.body()?.items!!}")

                                //검색 결과 데이터(썸네일) 추가하기
                                sd.add(
                                    SearchData(
                                        "",
                                        "",
                                        "${item.thumbnail}"
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
                            imageAdapter.addItem(sd)
                            editor.putString("image", reqArray.toString())
                            editor.apply()
                        }

                        override fun onFailure(call: Call<ResultGetSearch>, t: Throwable) {
                            Log.d("MainActivity", "실패 : $t")
                        }


                    })
                } else {
                    sd.clear()
                    imageAdapter.addItem(sd)
                }
            }

            binding.btnImageGet -> {
                sharedPreferences.getString("image", "")

                //shared prefrence 불러와서 json 파싱하기
                val jsonArray =
                    JSONTokener(sharedPreferences.getString("image", null)).nextValue() as JSONArray
                for (i in 0 until jsonArray.length()) {
                    val thumbnail = jsonArray.getJSONObject(i).getString("thumbnail")
                    sd.add(SearchData("", "", thumbnail, ""))
                }
                imageAdapter.addItem(sd)
            }
        }
    }

}