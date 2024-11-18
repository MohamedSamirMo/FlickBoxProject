package com.example.movieapp.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.adpater.MoviesAdapter
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.mvvm.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
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
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchBinding.bind(view)
        binding.recMovie.adapter = moviesAdapter

        moviesAdapter.onItemClickListener = object : MoviesAdapter.OnItemClickListener {
            override fun onItemClick(id: Int) {
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment2(id))
            }
        }

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        observeData()

        // إضافة TextWatcher لتحديث البحث تلقائيًا عند الكتابة
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                if (query.isNotEmpty()) {
                    searchMovies(query)  // إجراء البحث
                } else {
                    moviesAdapter.movieList = arrayListOf() // مسح القائمة إذا كان الحقل فارغًا
                    moviesAdapter.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeData() {
        moviesViewModel.moviesLiveData.observe(viewLifecycleOwner) { movieList ->
            binding.progressBar.visibility = View.GONE
            if (movieList != null && movieList.isNotEmpty()) {
                moviesAdapter.movieList = ArrayList(movieList)
                moviesAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "No movies found or an error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchMovies(query: String) {
        binding.progressBar.visibility = View.VISIBLE
        moviesViewModel.searchMovies(query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
