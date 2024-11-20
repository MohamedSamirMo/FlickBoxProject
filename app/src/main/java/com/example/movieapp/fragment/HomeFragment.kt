package com.example.movieapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R

import com.example.movieapp.adpater.MoviesAdapter
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.models.ResultPopular
import com.example.movieapp.models.ResultTop
import com.example.movieapp.mvvm.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val moviesAdapter by lazy {
        MoviesAdapter()
    }

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle item click navigation
        moviesAdapter.onItemClickListener = object : MoviesAdapter.OnItemClickListener {
            override fun onItemClick(id: Int ) {
                val navController = findNavController()
                navController.navigate(HomeFragmentDirections.actionHomeFragment2ToDetailsFragment2(id))


            }
        }

        _binding = FragmentHomeBinding.bind(view)
        observeData()

        binding.tabUpComing.setOnClickListener({
            moviesViewModel.getUpcomingMovies()
            observeDataUpcoming()


        })
        binding.tabNowPlay.setOnClickListener({

            moviesViewModel.getNowPlayingMovies()
            observeDataNowPlaying()

        })


        // Popular tab click listener
        binding.tabPopular.setOnClickListener {
            moviesViewModel.getPopularMovies() // Fetch popular movies
            observeDataPopular() // Start observing for popular movies

        }

        // Top rated tab click listener
        binding.tabTopRated.setOnClickListener {
            // Fetch top rated movies
            moviesViewModel.getTopRatedMovies()
            // Reset previous data to avoid repeating it in RecyclerView
            moviesViewModel.clearTopRatedData()

            // Observe data for top rated movies
            observeDataTopRating()


        }

        // Search icon click listener
        binding.iconSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_searchFragment)
        }
    }

    // Function to observe and update popular movie data
    private fun observeDataPopular() {
        binding.progressBar.visibility = View.VISIBLE

        // Observe popular movie data
        moviesViewModel.PopularLiveData.observe(viewLifecycleOwner) { popularList ->
            binding.progressBar.visibility = View.GONE
            if (popularList != null) {
                moviesAdapter.popularList = ArrayList(popularList)

            } else {
                moviesAdapter.popularList = ArrayList() // Clear list if no data
            }
            binding.recMovie.adapter = moviesAdapter
            moviesAdapter.notifyDataSetChanged()
        }
    }

    // Function to observe and update top rated movie data
    private fun observeDataTopRating() {
        binding.progressBar.visibility = View.VISIBLE

        // Observe top rated movie data
        moviesViewModel.TopRatedLiveData.observe(viewLifecycleOwner) { topRatingList ->
            binding.progressBar.visibility = View.GONE
            if (topRatingList != null) {
                moviesAdapter.topList = ArrayList(topRatingList)
            } else {
                moviesAdapter.topList = ArrayList() // Clear list if no data
            }
            binding.recMovie.adapter = moviesAdapter
            moviesAdapter.notifyDataSetChanged()
        }
    }
    private fun observeDataNowPlaying() {
        binding.progressBar.visibility = View.VISIBLE
        moviesViewModel.nowPlayingLiveData.observe(viewLifecycleOwner) { nowPlayingList ->
            binding.progressBar.visibility = View.GONE

            if (nowPlayingList != null) {
                moviesAdapter.nowPlayingList = ArrayList(nowPlayingList)
            } else {
                moviesAdapter.nowPlayingList = ArrayList()
            }
            binding.recMovie.adapter = moviesAdapter
            moviesAdapter.notifyDataSetChanged()
        }
    }

    private fun observeDataUpcoming(){
        binding.progressBar.visibility = View.VISIBLE
        moviesViewModel.upcomingLiveData.observe(viewLifecycleOwner){
                upcomingList->
            binding.progressBar.visibility = View.GONE

            if (upcomingList != null){
                moviesAdapter.upcomingList = ArrayList(upcomingList)
            }else{
                moviesAdapter.upcomingList = ArrayList()
            }
            binding.recMovie.adapter = moviesAdapter
            moviesAdapter.notifyDataSetChanged()
        }
    }

    // Observe all movie data from ViewModel (for other categories)
    private fun observeData() {
        binding.progressBar.visibility = View.VISIBLE

        // Observe movie data
        moviesViewModel.moviesLiveData.observe(viewLifecycleOwner) { movieList ->
            binding.progressBar.visibility = View.GONE
            if (movieList != null) {
                moviesAdapter.movieList = ArrayList(movieList)
                binding.recMovie.adapter = moviesAdapter
            }
        }
    }

    // Clear references when fragment is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
