package com.orgustine.moviesapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.orgustine.moviesapp.data.remote.DataState
import com.orgustine.moviesapp.data.MoviesViewModel
import com.orgustine.moviesapp.R
import com.orgustine.moviesapp.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieList : Fragment(R.layout.fragment_movie_list), OnItemClickListener {
    private lateinit var binding : FragmentMovieListBinding
    @Inject lateinit var viewModel: MoviesViewModel
    private val moviesAdapter = MoviesAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieListBinding.bind(view)

        viewModel.getMovies()
        subscribe()
        binding.list.apply {
            //layoutManager = GridLayoutManager(requireContext(), 2)
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = moviesAdapter
        }

    }

    private fun subscribe() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Waiting -> {
                    Snackbar.make(
                        requireView(), "Loading...", Snackbar.LENGTH_LONG
                    ).show()
                }
                is DataState.Error -> Snackbar.make(
                    requireView(),
                    "Error: ${dataState.exception.localizedMessage}",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Retry") { viewModel.getMovies() }
                    .show()
                is DataState.Success -> {
                    moviesAdapter.submitList(dataState.data.tv_shows)
                    binding.progress.visibility = View.GONE
                    binding.list.visibility = View.VISIBLE;
                }
            }
        })
    }

    override fun onItemClick(id: String) {
        val action = MovieListDirections.actionMovieListToDetailsFragment(id)
        findNavController().navigate(action)
    }

}