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


    fun getMovies() {
        viewModelScope.launch {
            moviesRepository.getAllMovies()
                .onEach { dataState ->
                    _dataState.value = dataState
                }.launchIn(viewModelScope)
        }
    }
}