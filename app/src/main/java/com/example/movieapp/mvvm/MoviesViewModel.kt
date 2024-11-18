package com.example.movieapp.mvvm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.DetailsModel
import com.example.movieapp.models.MovieResult
import com.example.movieapp.models.ResultPopular
import com.example.movieapp.models.ResultTop
import com.example.movieapp.respository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(val moviesRepository: MoviesRepository) : ViewModel() {

    val _moviesLiveData = MutableLiveData<List<MovieResult>?>()
    val moviesLiveData get() = _moviesLiveData

    val _PopularLiveData = MutableLiveData<List<ResultPopular>>()
    val PopularLiveData get() = _PopularLiveData

    val _TopRatedLiveData = MutableLiveData<List<ResultTop>>()
    val TopRatedLiveData get() = _TopRatedLiveData



    val _errorLiveData = MutableLiveData<String>()
    val errorLiveData get() = _errorLiveData
    val _loading=MutableLiveData<Boolean>()
    val loading get() = _loading

    // تعديل نوع الـ LiveData الخاص بتفاصيل الفيلم
    private val _movieDetailsLiveData = MutableLiveData<DetailsModel?>()
    val movieDetailsLiveData get() = _movieDetailsLiveData

    val videoLiveData = MutableLiveData<String?>()

    init {
        getMovies()
        getPopularMovies()
    }

    private fun getMovies() {
        viewModelScope.launch(IO) {

            try {
                _loading.postValue(true)
                val data = moviesRepository.getAllMoviesFromPages()
                _moviesLiveData.postValue(data)
                _loading.postValue(false)
            } catch (e: Exception) {
                _errorLiveData.postValue(e.message)
            }
        }
    }

    // Fetch popular movies
    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                val movies = moviesRepository.getPopularMovies()
                _PopularLiveData.postValue(movies)
                _loading.postValue(false)
            } catch (e: Exception) {
                _PopularLiveData.postValue(emptyList())
                _errorLiveData.postValue(e.message)
            }
        }
    }

    // Fetch top-rated movies
    fun getTopRatedMovies() {
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                val movies = moviesRepository.getTopRatedMovies()
                _TopRatedLiveData.postValue(movies)
                _loading.postValue(false)
            } catch (e: Exception) {
                _TopRatedLiveData.postValue(emptyList())
                _errorLiveData.postValue(e.message)
            }
        }
    }

    fun clearPopularData() {
        _PopularLiveData.value = emptyList()
    }

    fun clearTopRatedData() {
        _TopRatedLiveData.value = emptyList()
    }
    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                // البحث في المستودع بناءً على النص المدخل
                val allMovies = moviesRepository.getAllMoviesFromPages() // جلب جميع الأفلام من المستودع
                val filteredMovies = allMovies?.filter {
                    it.title!!.contains(query, ignoreCase = true)
                }
                // تحديث LiveData بالنتائج المفلترة
                _moviesLiveData.postValue(filteredMovies)
            } catch (e: Exception) {
                Log.e("MoviesViewModel", "Error fetching movies: ${e.message}")
                _moviesLiveData.postValue(emptyList()) // في حالة الخطأ، نشر قائمة فارغة
            }
        }
    }
    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(IO) {
            try {
                // جلب تفاصيل الفيلم من المستودع
                val movieDetails = moviesRepository.getMovieDetails(movieId)
                _movieDetailsLiveData.postValue(movieDetails)
            } catch (e: Exception) {
                _errorLiveData.postValue(e.message)
            }
        }
    }
}
