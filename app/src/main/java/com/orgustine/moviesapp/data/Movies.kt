package com.orgustine.moviesapp.data

data class Movies(
    val page: Int,
    val pages: Int,
    val total: String,
    val tv_shows: List<TvShow>
)