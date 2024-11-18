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
import com.example.movieapp.adpater.MoviesPagerAdapter
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
        moviesAdapter.onItemClickListener = object : MoviesAdapter.OnItemClickListener {
            override fun onItemClick(id: Int) {
                val navController = findNavController()

                // Check if the current destination is not already DetailsFragment2
                if (navController.currentDestination?.id != R.id.detailsFragment2) {
                    navController.navigate(HomeFragmentDirections.actionHomeFragment2ToDetailsFragment2(id))
                }
            }
        }

        _binding = FragmentHomeBinding.bind(view)

        // Observe data from ViewModel
        observeData()

        // On click for Popular Tab
        binding.tabPopular.setOnClickListener {
            moviesViewModel.clearPopularData()
            observeDataPopular()
            moviesViewModel.getPopularMovies()

        }

        // On click for Top Rated Tab
        binding.tabTopRated.setOnClickListener {
            moviesViewModel.clearTopRatedData()
            observeDataTopRating()
            moviesViewModel.getTopRatedMovies()
        }

        // On click for Search Icon, navigate to SearchFragment
        binding.iconSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_searchFragment)
        }

//        // On click for Search Icon
//        binding.iconSearch.setOnClickListener {
//            val query = binding.searchInput.text.toString()
//            if (query.isNotEmpty()) {
//                searchMovies(query)
//            } else {
//                Toast.makeText(requireContext(), "Please enter a search term", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    private fun observeData() {
        binding.progressBar.visibility = View.VISIBLE

        // Observe movie data from ViewModel
        moviesViewModel.moviesLiveData.observe(viewLifecycleOwner) { movieList ->
            binding.progressBar.visibility = View.GONE
            if (movieList != null) {
                moviesAdapter.movieList = ArrayList(movieList)
                binding.recMovie.adapter = moviesAdapter
            }
        }
    }
    private fun observeDataPopular() {
        binding.progressBar.visibility = View.VISIBLE

        // Observe movie data from ViewModel
        moviesViewModel.PopularLiveData.observe(viewLifecycleOwner) { popularList ->
            binding.progressBar.visibility = View.GONE
            if (popularList != null && popularList.isNotEmpty()) {
                // إذا كانت القائمة تحتوي على بيانات
                moviesAdapter.popularList = ArrayList(popularList)
            } else {
                // إذا كانت القائمة فارغة، قم بتعيين قائمة فارغة

                moviesAdapter.popularList = ArrayList() // تأكد من أن تكون القائمة فارغة بشكل صحيح
            }
            binding.recMovie.adapter = moviesAdapter
            moviesAdapter.notifyDataSetChanged()
        }
    }

    private fun observeDataTopRating() {
        binding.progressBar.visibility = View.VISIBLE

        // Observe movie data from ViewModel
        moviesViewModel.TopRatedLiveData.observe(viewLifecycleOwner) { topRatingList ->
            binding.progressBar.visibility = View.GONE
            if (topRatingList != null && topRatingList.isNotEmpty()) {
                // إذا كانت القائمة تحتوي على بيانات
                moviesAdapter.topList = ArrayList(topRatingList)
            } else {
                // إذا كانت القائمة فارغة، قم بتعيين قائمة فارغة

                moviesAdapter.topList = ArrayList() // تأكد من أن تكون القائمة فارغة بشكل صحيح
            }
            binding.recMovie.adapter = moviesAdapter
            moviesAdapter.notifyDataSetChanged()
        }
    }






//    private fun fetchMovies() {
//
//        when (currentCategory) {
//            "Popular" -> moviesViewModel.getPopularMovies()
//            "Top Rated" -> moviesViewModel.getTopRatedMovies()
//        }
//    }

    private fun searchMovies(query: String) {
        moviesViewModel.searchMovies(query) // Pass the query to ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
