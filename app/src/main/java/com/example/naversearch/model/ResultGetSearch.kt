package com.example.naversearch

data class ResultGetSearch(
    var lastBuildDate: String = "",
    var total: Int = 0,
    var start: Int = 0,
    var display: Int = 0,
    var resultGetSearchItem: List<ResultGetSeachItem>
)

data class ResultGetSeachItem(
    var title: String = "",
    var originallink: String = "",
    var link: String = "",
    var description: String = "",
    var pubDate: String = "",
    var thumbnail: String = "",
    var sizeheight: String = "100",
    var sizewidth: String = "100"
)

