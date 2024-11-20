package com.example.movieapp.mvvm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.DetailsModel
import com.example.movieapp.models.MovieResult
import com.example.movieapp.models.ResultPlay
import com.example.movieapp.models.ResultPopular
import com.example.movieapp.models.ResultTop
import com.example.movieapp.models.ResultUpComing
import com.example.movieapp.respository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(val moviesRepository: MoviesRepository) : ViewModel() {


    val _moviesLiveData = MutableLiveData<List<MovieResult>?>()
    val moviesLiveData get() = _moviesLiveData

    val _PopularLiveData = MutableLiveData<List<ResultPopular>?>()
    val PopularLiveData get() = _PopularLiveData

    val _TopRatedLiveData = MutableLiveData<List<ResultTop>?>()
    val TopRatedLiveData get() = _TopRatedLiveData


    val _nowPlayingLiveData = MutableLiveData<List<ResultPlay>?>()
    val nowPlayingLiveData get() = _nowPlayingLiveData
    val _upcomingLiveData = MutableLiveData<List<ResultUpComing>?>()
    val upcomingLiveData get() = _upcomingLiveData

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
        // Launch the coroutine in the IO dispatcher (for network call)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Indicate that loading has started
                _loading.postValue(true)

                // Fetch popular movies from the repository
                val movies = moviesRepository.getPopularMovies()

                // Post the fetched data to the LiveData on the main thread
                withContext(Dispatchers.Main) {
                    _PopularLiveData.postValue(movies)
                    _loading.postValue(false) // Set loading to false after data is fetched
                }

            } catch (e: Exception) {
                // Post empty list if there's an error
                withContext(Dispatchers.Main) {
                    _PopularLiveData.postValue(emptyList())  // Provide an empty list on error
                    _errorLiveData.postValue(e.localizedMessage ?: "An error occurred")  // Show error message
                    _loading.postValue(false)  // Ensure loading state is reset even in case of error
                }
            }
        }
    }


    // Fetch top-rated movies
    fun getTopRatedMovies() {
        // Launch the coroutine in the IO dispatcher (for network call)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Indicate that loading has started
                _loading.postValue(true)

                // Fetch top-rated movies from the repository
                val movies = moviesRepository.getTopRatedMovies()

                // Post the fetched data to the LiveData on the main thread
                withContext(Dispatchers.Main) {
                    _TopRatedLiveData.postValue(movies)
                    _loading.postValue(false) // Set loading to false after data is fetched
                }

            } catch (e: Exception) {
                // Post empty list if there's an error
                withContext(Dispatchers.Main) {
                    _TopRatedLiveData.postValue(emptyList())  // Provide an empty list on error
                    _errorLiveData.postValue(e.localizedMessage ?: "An error occurred")  // Show error message
                    _loading.postValue(false)  // Ensure loading state is reset even in case of error
                }
            }
        }
    }
    fun getUpcomingMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                val data = moviesRepository.getUpcomingMovies()
                _upcomingLiveData.postValue(data)
                _loading.postValue(false)
            }
            catch (e:Exception){
                _errorLiveData.postValue(e.message)
            }
        }

    }
    fun getNowPlayingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                val data = moviesRepository.getNowPlayingMovies()
                _nowPlayingLiveData.postValue(data)
                _loading.postValue(false)
            } catch (e: Exception) {
                _errorLiveData.postValue(e.message)
            }

        }
    }

    fun clearPopularData() {
        _PopularLiveData.postValue(emptyList())
    }

    fun clearTopRatedData() {
        _TopRatedLiveData.postValue(emptyList())
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
