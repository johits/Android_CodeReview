package com.example.naversearch.model

data class SearchData(
    // 게시글 제목
    val title: String = "",

    // 게시글 내용
    val description: String = "",

    //이미지
    val thumbnail:String? = "",

    //하이퍼텍스트 link
    val link:String = ""
)
