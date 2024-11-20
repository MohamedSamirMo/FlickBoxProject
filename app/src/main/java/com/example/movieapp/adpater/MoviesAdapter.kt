package com.example.movieapp.adpater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.models.MovieResult
import com.example.movieapp.models.ResultPlay
import com.example.movieapp.models.ResultPopular
import com.example.movieapp.models.ResultTop
import com.example.movieapp.models.ResultUpComing

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.Holder>() {

    var movieList: ArrayList<MovieResult>? = null
    var topList: ArrayList<ResultTop>? = null
    var popularList: ArrayList<ResultPopular>? = null
    var nowPlayingList: ArrayList<ResultPlay>? = null
    var upcomingList: ArrayList<ResultUpComing>? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemMovieBinding.inflate(android.view.LayoutInflater.from(parent.context), parent, false))

    }

    override fun getItemCount(): Int {
        // أعد الحجم بناءً على القائمة التي تحتوي على بيانات
        return when {
            movieList != null -> movieList!!.size
            topList != null -> topList!!.size
            popularList != null -> popularList!!.size
            nowPlayingList != null -> nowPlayingList!!.size
            upcomingList != null -> upcomingList!!.size
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // تأكد من أن الفهرس ضمن الحدود للقوائم المختلفة
        movieList?.let {
            if (position < it.size) {
                holder.bind(it[position])
            }
        }
        topList?.let {
            if (position < it.size) {
                holder.bindTop(it[position])
            }
        }
        popularList?.let {
            if (position < it.size) {
                holder.bindPopular(it[position])
            }
        }
        nowPlayingList?.let {
            if (position < it.size) {
                holder.bindNowPlaying(it[position])
            }
        }
        upcomingList?.let {
            if (position < it.size) {
                holder.bindUpcoming(it[position])
            }
        }
    }

    inner class Holder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                // التأكد من أن الفهرس صالح في كل قائمة
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    when {
                        movieList != null && position < movieList!!.size -> {
                            movieList?.get(position)?.id?.let { it1 -> onItemClickListener?.onItemClick(it1) }
                        }
                        topList != null && position < topList!!.size -> {
                            topList?.get(position)?.id?.let { it1 -> onItemClickListener?.onItemClick(it1) }
                        }
                        popularList != null && position < popularList!!.size -> {
                            popularList?.get(position)?.id?.let { it1 -> onItemClickListener?.onItemClick(it1) }
                        }
                        nowPlayingList != null && position < nowPlayingList!!.size -> {
                            nowPlayingList?.get(position)?.id?.let { it1 -> onItemClickListener?.onItemClick(it1) }
                        }
                        upcomingList != null && position < upcomingList!!.size -> {
                            upcomingList?.get(position)?.id?.let { it1 -> onItemClickListener?.onItemClick(it1) }
                        }
                    }
                }
            }
        }

        fun bind(movieResult: MovieResult) {
            binding.apply {
                movieTitle.text = movieResult.title
                movieRating.text = "⭐  " + movieResult.vote_average.toString() + " / 10"
                movieDescription.text = movieResult.overview
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500" + movieResult.poster_path)
                    .into(moviePoster)
            }
        }

        fun bindTop(resultTop: ResultTop) {
            binding.apply {
                movieTitle.text = resultTop.title
                movieRating.text = "⭐  " + resultTop.vote_average.toString() + " / 10"
                movieDescription.text = resultTop.overview
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500" + resultTop.poster_path)
                    .into(moviePoster)
            }
        }

        fun bindPopular(resultPopular: ResultPopular) {
            binding.apply {
                movieTitle.text = resultPopular.title
                movieRating.text = "⭐  " + resultPopular.vote_average.toString() + " / 10"
                movieDescription.text = resultPopular.overview
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500" + resultPopular.poster_path)
                    .into(moviePoster)
            }
        }
        fun bindNowPlaying(resultPlay: ResultPlay) {
            binding.apply {
                movieTitle.text = resultPlay.title
                movieRating.text = "⭐  " + resultPlay.vote_average.toString() + " / 10"
                movieDescription.text = resultPlay.overview
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500" + resultPlay.poster_path)
                    .into(moviePoster)

            }

        }
        fun bindUpcoming (resultUpComing: ResultUpComing){
            binding.apply {
                movieTitle.text = resultUpComing.title
                movieRating.text = "⭐  " + resultUpComing.vote_average.toString() + " / 10"
                movieDescription.text = resultUpComing.overview
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500" + resultUpComing.poster_path)
                    .into(moviePoster)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

    fun clearData() {
        movieList = null
        topList = null
        popularList = null
        nowPlayingList = null
        upcomingList = null
        notifyDataSetChanged()
    }


}
