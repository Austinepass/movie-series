package com.orgustine.moviesapp.data

import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) {
    suspend fun getAllMovies() = remoteDataSource.getAllMovies()
}