package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.size.Scale
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.ezatpanah.hilt_retrofit_paging_youtube.adapter.AdapterActors
import com.ezatpanah.hilt_retrofit_paging_youtube.api.ApiServices
import com.ezatpanah.hilt_retrofit_paging_youtube.databinding.FragmentMovieDetailesBinding
import com.ezatpanah.hilt_retrofit_paging_youtube.utils.POSTER_BASE_URL
import com.ezatpanah.hilt_retrofit_paging_youtube.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailesBinding
    private var movieId = 0
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MoviesViewModel by viewModels()

    val TAG = "MovieDetailsFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMovieDetailesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = args.movieId
        if (movieId > 0) {
            viewModel.loadDetailsMovie(movieId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleScope.launch {
                val retrofit =
                    Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").addConverterFactory(
                        GsonConverterFactory.create()
                    ).build()
                val a = retrofit.create(ApiServices::class.java).getActors(movieId)
                Log.e("qqq", a.cast.toString())
                rvActors.apply {
                    val mAdapterActors = AdapterActors()
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    adapter = mAdapterActors.apply {
                        setData(a.cast)
                    }
                }

            }


            viewModel.detailsMovie.observe(viewLifecycleOwner) {response->
                val moviePosterURL = POSTER_BASE_URL + response.posterPath
                imgMovie.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                imgMovieBack.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }

                tvMovieTitle.text = response.title
                tvMovieTagLine.text = response.tagline
                tvMovieDateRelease.text = response.releaseDate
                tvMovieRating.text = response.voteAverage.toString()
                tvMovieRuntime.text = response.runtime.toString() +"m"
                tvMovieBudget.text = response.budget.toString() + "$"
                tvMovieRevenue.text = response.revenue.toString()
                tvMovieOverview.text = response.overview
            }

            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    prgBarMovies.visibility = View.VISIBLE
                } else {
                    prgBarMovies.visibility = View.INVISIBLE
                }
            }


        }
    }

}