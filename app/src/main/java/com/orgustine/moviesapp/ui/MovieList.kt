package com.orgustine.moviesapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.orgustine.moviesapp.data.remote.DataState
import com.orgustine.moviesapp.MoviesAdapter
import com.orgustine.moviesapp.data.MoviesViewModel
import com.orgustine.moviesapp.R
import com.orgustine.moviesapp.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieList : Fragment(R.layout.fragment_movie_list) {
    private lateinit var binding : FragmentMovieListBinding
    @Inject lateinit var viewModel: MoviesViewModel
    private val moviesAdapter = MoviesAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieListBinding.bind(view)

        viewModel.getMovies()
        subscribe()
        binding.list.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = moviesAdapter
        }

    }

    private fun subscribe() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Waiting -> Snackbar.make(
                    requireView(), "Loading...", Snackbar.LENGTH_LONG
                ).show()
                is DataState.Error -> Snackbar.make(
                    requireView(),
                    "Error: ${dataState.exception.localizedMessage}",
                    Snackbar.LENGTH_LONG
                ).show()
                is DataState.Success -> {
                    moviesAdapter.submitList(dataState.data.tv_shows)
                    Log.i("Movies", dataState.data.toString())
                }
            }
        })
    }

}