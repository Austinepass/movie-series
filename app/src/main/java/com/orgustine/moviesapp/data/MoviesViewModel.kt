package com.orgustine.moviesapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orgustine.moviesapp.data.remote.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _dataState = MutableLiveData<DataState<Movies>>()
    val dataState: MutableLiveData<DataState<Movies>>
        get() = _dataState

    private val _movieDetails = MutableLiveData<DataState<Movie>>()
    val movieDetails: MutableLiveData<DataState<Movie>>
        get() = _movieDetails


    fun getMovies() {
        viewModelScope.launch {
            moviesRepository.getAllMovies()
                .onEach { dataState ->
                    _dataState.value = dataState
                }.launchIn(viewModelScope)
        }
    }
    fun getMovieDetails(id: String) {
        viewModelScope.launch {
            moviesRepository.getMovie(id)
                .onEach {
                    _movieDetails.value = it
                }.launchIn(viewModelScope)
        }
    }
}