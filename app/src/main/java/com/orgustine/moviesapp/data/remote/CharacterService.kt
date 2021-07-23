package com.orgustine.moviesapp.data.remote

import com.orgustine.moviesapp.data.Movies
import com.orgustine.moviesapp.data.TvShow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {
    @GET("most-popular?page=1")
    suspend fun getAllMovies() : Movies

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<TvShow>
}
