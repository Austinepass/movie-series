package com.orgustine.moviesapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.orgustine.moviesapp.R
import com.orgustine.moviesapp.data.Movie
import com.orgustine.moviesapp.data.MoviesViewModel
import com.orgustine.moviesapp.data.TvShowX
import com.orgustine.moviesapp.data.remote.DataState
import com.orgustine.moviesapp.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var binding : FragmentDetailsBinding
    @Inject lateinit var viewModel : MoviesViewModel
    private lateinit var link: String
    private val args : DetailsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)
        viewModel.getMovieDetails(args.id)
        subscribe()

        binding.fab.setOnClickListener {
            binding.detailScreen.visibility = View.GONE
            binding.webview.apply {
                visibility = View.VISIBLE
                loadUrl(link)
                webViewClient = WebViewClient()
            }
        }



    }

    private fun subscribe() {
        viewModel.movieDetails.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Waiting -> {
                    Snackbar.make(
                        requireView(), "Loading...", Snackbar.LENGTH_LONG
                    ).show()
                    binding.shim.startShimmer()
                }
                is DataState.Error -> Snackbar.make(
                    requireView(),
                    "Error: ${it.exception.localizedMessage}",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Retry") { viewModel.getMovieDetails(args.id) }
                    .show()
                is DataState.Success -> {
                    //moviesAdapter.submitList(dataState.data.tv_shows)
                    setViews(it.data.tvShow)
                    binding.shim.stopShimmer()
                    binding.shim.hideShimmer()
                }
            }
        })
    }
    private fun setViews(tvShow: TvShowX) {
        binding.apply {
            Glide.with(requireActivity()).load(tvShow.image_path).into(image)
            var genre = ""
            tvShow.genres.forEach {
                genre += if (it == tvShow.genres.last()) it else "$it, "
            }
            genreTv.text = genre
            movieNameTv.text = tvShow.name
            aboutTv.text = tvShow.description.replace("<b>", "").replace("</b>", "")
            ratingBar.rating = tvShow.rating.toFloat()/2
            link = tvShow.url

        }
    }


}