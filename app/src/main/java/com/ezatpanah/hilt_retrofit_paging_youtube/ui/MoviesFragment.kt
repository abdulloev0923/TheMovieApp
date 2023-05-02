package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.ViewParentCompat.notifySubtreeAccessibilityStateChanged
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezatpanah.hilt_retrofit_paging_youtube.adapter.AdapterActors
import com.ezatpanah.hilt_retrofit_paging_youtube.adapter.AdapterGenres
import com.ezatpanah.hilt_retrofit_paging_youtube.adapter.LoadMoreAdapter
import com.ezatpanah.hilt_retrofit_paging_youtube.adapter.MoviesAdapter
import com.ezatpanah.hilt_retrofit_paging_youtube.api.ApiServices
import com.ezatpanah.hilt_retrofit_paging_youtube.databinding.FragmentMoviesBinding
import com.ezatpanah.hilt_retrofit_paging_youtube.repository.ApiRepository
import com.ezatpanah.hilt_retrofit_paging_youtube.response.MoviesListResponse
import com.ezatpanah.hilt_retrofit_paging_youtube.utils.API_KEY
import com.ezatpanah.hilt_retrofit_paging_youtube.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    @Inject
    lateinit var moviesAdapter: MoviesAdapter
    private val viewModel: MoviesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {


            lifecycleScope.launchWhenCreated {
                viewModel.moviesList.collect {
                    moviesAdapter.submitData(it)

                }


            }
            val searchAdapter = MoviesAdapter()


            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
if (newText != null) {

    lifecycleScope.launchWhenCreated {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getSearch(query = newText).collectLatest {

                searchAdapter.submitData(it)
                Log.e("qqqqqqqqqq", searchAdapter.toString())
                rlMovies.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = searchAdapter.apply {
                        setOnItemClickListener {
                            val direction =
                                MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                                    it.id
                                )
                            findNavController().navigate(direction)

                        }

                    }
                }

            }


        }
    }
}
                    else{
                        rlMovies.layoutManager == LinearLayoutManager(requireContext())
                        rlMovies.adapter = moviesAdapter
                    }
                    return true
                }
            })







            val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").addConverterFactory(
                GsonConverterFactory.create()
            ).build()
            lifecycleScope.launch {
                val genre = retrofit.create(ApiServices::class.java).getGenre()
                rvGenres.apply {
                    val mAdapterGenres = AdapterGenres()
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    adapter = mAdapterGenres.apply {
                        setData(genre.genres)
                        setOnClick {g->
                            if(g.id == 1 && g.name == "Popular Now"){

                                    lifecycleScope.launchWhenCreated {
                                        viewModel.moviesList.collect {
                                            moviesAdapter.submitData(it)

                                        }

                                    }
                                rlMovies.apply {
                                    layoutManager = LinearLayoutManager(requireContext())
                                    adapter = moviesAdapter.apply {
                                        setOnItemClickListener {
                                            val direction =
                                                MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                                                    it.id
                                                )
                                            findNavController().navigate(direction)

                                        }
                                    }
                                }


                            }else if(g.id == 2 && g.name == "Now Playing"){
                                val now_playing = MoviesAdapter()
                                lifecycleScope.launchWhenCreated {
                                    viewModel.nowPlayingList.collect {
                                        now_playing.submitData(it)

                                    }

                                }
                                rlMovies.apply {
                                    layoutManager = LinearLayoutManager(requireContext())
                                    adapter = now_playing.apply {
                                        setOnItemClickListener {
                                            val direction =
                                                MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                                                    it.id
                                                )
                                            findNavController().navigate(direction)

                                        }
                                    }
                                }

                            }
                            else if(g.id == 3 && g.name == "Top Rated"){
                                val top_rated = MoviesAdapter()
                                lifecycleScope.launchWhenCreated {
                                    viewModel.TopRatedList.collect {
                                        top_rated.submitData(it)
                                    }

                                }
                                rlMovies.apply {
                                    layoutManager = LinearLayoutManager(requireContext())
                                    adapter = top_rated.apply {
                                        setOnItemClickListener {
                                            val direction =
                                                MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                                                    it.id
                                                )
                                            findNavController().navigate(direction)

                                        }
                                    }
                                }

                            }
                            else if(g.id == 4 && g.name == "Upcoming"){
                                val upcoming = MoviesAdapter()
                                lifecycleScope.launchWhenCreated {
                                    viewModel.Upcoming.collect {
                                        upcoming.submitData(it)

                                    }

                                }
                                rlMovies.apply {
                                    layoutManager = LinearLayoutManager(requireContext())
                                    adapter = upcoming.apply {
                                        setOnItemClickListener {
                                            val direction =
                                                MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                                                    it.id
                                                )
                                            findNavController().navigate(direction)

                                        }
                                    }
                                }

                            }

                            else {

                                val genress = MoviesAdapter()
                                lifecycleScope.launchWhenCreated {
                                    viewModel.moviesList.collect {
                                        genress.submitData(invoke(it, genre_ids = g.id))

                                    }
                                }
                                rlMovies.apply {
                                    layoutManager = LinearLayoutManager(requireContext())
                                    adapter = genress.apply {
                                        setOnItemClickListener {
                                            val direction =
                                                MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                                                    it.id
                                                )
                                            findNavController().navigate(direction)

                                        }
                                    }
                                }

                            }

                        }
                    }
                }
            }








            lifecycleScope.launchWhenCreated {
                moviesAdapter.loadStateFlow.collect{
                    val state = it.refresh
                    prgBarMovies.isVisible = state is LoadState.Loading
                }
            }



            rlMovies.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = moviesAdapter.apply {
                    setOnItemClickListener {
                        val direction =
                            MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                                it.id
                            )
                        findNavController().navigate(direction)

                    }
                }
            }


            rlMovies.adapter=moviesAdapter.withLoadStateFooter(
                LoadMoreAdapter{
                    moviesAdapter.retry()
                }
            )

        }


    }

    operator fun invoke(movies:PagingData<MoviesListResponse.Result>, genre_ids:Int):PagingData<MoviesListResponse.Result>{
        return movies.filter {
            it.genreIds.contains(genre_ids)
        }
    }

}