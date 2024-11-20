package com.example.movieapp.respository

import com.example.movieapp.local.MyDao
import com.example.movieapp.models.DetailsModel
import com.example.movieapp.models.MovieResult
import com.example.movieapp.models.PopularModel
import com.example.movieapp.models.ResultPlay
import com.example.movieapp.models.ResultPopular
import com.example.movieapp.models.ResultTop
import com.example.movieapp.models.ResultUpComing
import com.example.movieapp.models.TopRatingModel
import com.example.movieapp.network.ApiServices
import javax.inject.Inject

class MoviesRepository @Inject constructor(val dao: MyDao, val apiServices: ApiServices) {

    // جلب الأفلام من الصفحات 1 إلى 5
    suspend fun getAllMoviesFromPages(): List<MovieResult>? {
        val allMovies = mutableListOf<MovieResult>() // قائمة لتخزين جميع الأفلام

        try {
            // حلقة لجلب الأفلام من الصفحات 1 إلى 100
            for (page in 1..30) {
                val movieResponse = apiServices.getMovies(page)
                movieResponse.results?.let { movies ->
                    allMovies.addAll(movies) // إضافة الأفلام إلى القائمة
                }
            }

            // بعد جلب كل الصفحات، إدراج الأفلام في قاعدة البيانات
            insertPost(allMovies)

        } catch (e: Exception) {
            // في حال حدوث استثناء، جلب البيانات من الكاش
            return getFromCache()
        }

        return allMovies // إرجاع جميع الأفلام
    }
//    // جلب الأفلام من الشبكة أو قاعدة البيانات
//    suspend fun getMovie(): List<MovieResult>? {
//        return try {
//            // حاول الحصول على الأفلام من الشبكة
//            val data = apiServices.getMovies(
//                page = 1
//            )
//            // إدراج الأفلام في قاعدة البيانات
//            insertPost(data.results ?: emptyList())
//            data.results
//        } catch (e: Exception) {
//            // في حال وجود استثناء، جلب البيانات من الكاش (قاعدة البيانات المحلية)
//            getFromCache()
//        }
//    }





    // Fetch popular movies from the API
    suspend fun getPopularMovies(): List<ResultPopular> {

        try {
            // Extract the results from the response
            val response = apiServices.getPopularMovies(

            )
            return response.results ?: emptyList()
        }catch (e:Exception){
            getFromCache()
        }

        return emptyList()
    }

    suspend fun searchMovies(query: String): List<MovieResult>? {
        return try {
            // محاولة جلب البيانات من الشبكة
            val data = apiServices.searchMovies(query)

            // حفظ البيانات في الكاش أو قاعدة البيانات المحلية بعد جلبها
            val results = data.results ?: emptyList()
            insertPost(results)

            // إعادة النتائج المسترجعة من الشبكة
            results
        } catch (e: Exception) {
            // في حال حدوث خطأ، جلب البيانات من الكاش
            getFromCache()
        }



    }
    // Fetch top-rated movies from the API
    suspend fun getTopRatedMovies(): List<ResultTop> {
        try {
            val response = apiServices.getTopRatedMovies()
            return response.results ?: emptyList() // Extract the results from the response

        }catch (e:Exception){
            getFromCache()
        }
        return emptyList()

    }
    suspend fun getUpcomingMovies(): List<ResultUpComing> {
        try {
            val response = apiServices.getUpcomingMovies()
            return response.results ?: emptyList() // Extract the results from the response

        }catch (e:Exception){
            getFromCache()
        }
        return emptyList()

    }
    suspend fun getNowPlayingMovies(): List<ResultPlay> {
        try {
            val response = apiServices.getNowPlayingMovies()
            return response.results ?: emptyList() // Extract the results from the response
        } catch (e: Exception) {
            getFromCache()
        }
        return emptyList()
    }
    // جلب تفاصيل الفيلم من الشبكة أو الكاش
    suspend fun getMovieDetails(movieId: Int): DetailsModel? {
        return try {
            // حاول جلب التفاصيل من الشبكة
            val data = apiServices.getMovieDetails(movieId)
            // إدراج التفاصيل في قاعدة البيانات
            insertMovieDetails(data)
            data
        } catch (e: Exception) {
            // في حال وجود استثناء، جلب البيانات من الكاش
            getDetailsFromCache(movieId)
        }
    }






    // إدراج الأفلام في قاعدة البيانات
    private suspend fun insertPost(list: List<MovieResult>) {
        dao.insertMovie(list)
    }

    // جلب البيانات من الكاش (قاعدة البيانات المحلية)
    suspend fun getFromCache(): List<MovieResult> {
        return dao.getAllMovies()
    }

    // إدراج تفاصيل الفيلم في قاعدة البيانات
    private suspend fun insertMovieDetails(details: DetailsModel) {
        dao.insertDetails(details)
    }

    // جلب تفاصيل الفيلم من الكاش بناءً على ID
    private suspend fun getDetailsFromCache(id: Int): DetailsModel? {
        return dao.getDetailsById(id)
    }
}
