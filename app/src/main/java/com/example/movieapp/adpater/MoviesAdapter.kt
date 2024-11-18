package com.example.movieapp.adpater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.models.MovieResult
import com.example.movieapp.models.ResultPopular
import com.example.movieapp.models.ResultTop

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.Holder>() {

    var movieList: ArrayList<MovieResult>? = null
    var topList: ArrayList<ResultTop>? = null
    var popularList: ArrayList<ResultPopular>? = null

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
                holder.bind(it[position])
            }
        }
        popularList?.let {
            if (position < it.size) {
                holder.bind(it[position])
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

        fun bind(resultTop: ResultTop) {
            binding.apply {
                movieTitle.text = resultTop.title
                movieRating.text = "⭐  " + resultTop.vote_average.toString() + " / 10"
                movieDescription.text = resultTop.overview
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500" + resultTop.poster_path)
                    .into(moviePoster)
            }
        }

        fun bind(resultPopular: ResultPopular) {
            binding.apply {
                movieTitle.text = resultPopular.title
                movieRating.text = "⭐  " + resultPopular.vote_average.toString() + " / 10"
                movieDescription.text = resultPopular.overview
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w500" + resultPopular.poster_path)
                    .into(moviePoster)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }
    fun clearData() {
        movieList?.clear()
        topList?.clear()
        popularList?.clear()
        notifyDataSetChanged()  // Refresh the adapter
    }

}
