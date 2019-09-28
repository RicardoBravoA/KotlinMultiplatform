package com.rba.mpp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.AppCompatImageView
import kotlinx.android.synthetic.main.item_movie.view.*
import com.bumptech.glide.Glide

class MovieAdapter(
    private val onClickMovie: (MovieItemResponse) -> Unit
) :
    RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    var movieApi = MovieApi()

    var list: List<MovieItemResponse> by diffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    fun api(api: MovieApi){
        movieApi = api
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder =
        MovieHolder(parent.inflate(R.layout.item_movie, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = list[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { onClickMovie(movie) }
    }

    inner class MovieHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        fun bind(movieItemResponse: MovieItemResponse) {
            itemView.tvTitle.text = movieItemResponse.title
            Glide.with(itemView.ivMovie).load("https://image.tmdb.org/t/p/w185/${movieItemResponse.poster_path}").into(itemView.ivMovie)
        }
    }
}
