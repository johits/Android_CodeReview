package com.example.naversearch.model

import com.example.naversearch.ResultGetSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface NaverAPI {
    @Headers(
        "X-Naver-Client-Id:mq5H3u_f2qwPJUM1irHE",
        "X-Naver-Client-Secret:6aM7WmBUDV"
    )
    @GET("v1/search/{id}.json")
    fun getSearch(
        @Path("id") id: String,    // use @Path() instead of @Query()
        @Query("query") query: String,
        @Query("display") display: Int? = 9,
        @Query("start") start: Int? = null
    ): Call<ResultGetSearch>

}
