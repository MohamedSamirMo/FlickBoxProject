package com.example.movieapp.network

import com.example.movieapp.models.DetailsModel
import com.example.movieapp.models.MovieModel
import com.example.movieapp.models.MovieResult
import com.example.movieapp.models.NowPlayModel
import com.example.movieapp.models.PopularModel
import com.example.movieapp.models.ResultPopular
import com.example.movieapp.models.ResultTop
import com.example.movieapp.models.TopRatingModel

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    // Retrofit with Coroutines
    // الحصول على الأفلام مع دعم pagination عبر تمرير رقم الصفحة
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int
    ): MovieModel
@GET("discover/movie")
suspend fun searchMovies(@Query("query") query: String): MovieModel
//    @GET("movie/{movie_id}")
//    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieResult
    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(@Path("movie_id") movieId: Int): MovieResult
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): DetailsModel


    @GET("movie/popular")
    suspend fun getPopularMovies( @Query("page") page: Int): PopularModel

    // جلب الأفلام ذات التقييم الأعلى
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies( @Query("page") page: Int): TopRatingModel

    // جلب الأفلام التي تُعرض حالياً
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): NowPlayModel

}
// Retrofit with Rxjava
//@GET("posts")
//fun getPosts(): Single<List<PostModelX>>
//
//
//@GET("comments")
//fun getCommentsByPostId(@Query("postId") postId: Int): Single<List<CommentsModel>>