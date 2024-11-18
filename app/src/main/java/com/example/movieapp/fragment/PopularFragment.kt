package com.example.movieapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movieapp.R
import com.example.movieapp.adpater.MoviesAdapter
import com.example.movieapp.databinding.FragmentPopularBinding
import com.example.movieapp.models.ResultPopular
import com.example.movieapp.mvvm.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private val moviesAdapter by lazy {
        MoviesAdapter()
    }

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPopularBinding.bind(view)

        // إعداد الـ RecyclerView لعرض أفلام "Popular"
        binding.recMovie.adapter = moviesAdapter

        // مراقبة البيانات من ViewModel لعرض بيانات "Popular"
        observeData()

        // جلب الأفلام الخاصة بالفئة "Popular"
        moviesViewModel.getPopularMovies()
    }

    private fun observeData() {
        binding.progressBar.visibility = View.VISIBLE

        // مراقبة البيانات في الـ ViewModel
        moviesViewModel.moviesLiveData.observe(viewLifecycleOwner) { popularMovies ->
            binding.progressBar.visibility = View.GONE
            if (popularMovies != null) {
                // تحديث قائمة الأفلام الخاصة بـ "Popular"
                moviesAdapter.popularList = popularMovies as ArrayList<ResultPopular>
                moviesAdapter.notifyDataSetChanged() // تحديث الـ Adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
