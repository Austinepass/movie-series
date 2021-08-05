package com.orgustine.moviesapp.data.remote

import com.orgustine.moviesapp.data.Movie
import com.orgustine.moviesapp.data.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("most-popular?page=1")
    suspend fun getAllMovies() : Movies

    @GET("show-details")
    suspend fun getMovie(@Query("q") id: String) : Movie
}
