package com.orgustine.moviesapp.data

import com.orgustine.moviesapp.data.remote.DataState
import com.orgustine.moviesapp.data.remote.MovieService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
) {
    suspend fun getAllMovies(): Flow<DataState<Movies>> = flow{
        emit(DataState.Waiting)
        try {
            val response = movieService.getAllMovies()
            emit(DataState.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
        }

}
