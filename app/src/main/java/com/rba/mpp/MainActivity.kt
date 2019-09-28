package com.rba.mpp

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlin.coroutines.CoroutineContext
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    private lateinit var movieApi: MovieApi

    override val coroutineContext: CoroutineContext
        get() = job + Main

    private val movieAdapter = MovieAdapter(::onClickMovie)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = Job()

        movieApi = MovieApi()

        setupRecyclerView()

        loadList()
    }

    private fun loadList() {
        movieApi.getMovieList(
            success = {
                launch(Main) {
                    movieAdapter.list = it
                    android.util.Log.i("z- data", it.toString())
                }
            },
            failure = ::handleError
        )
    }

    private fun setupRecyclerView() {
        rvData.adapter = movieAdapter
    }

    fun onClickMovie(movie: MovieItemResponse) {
        android.util.Log.i("z- click", movie.toString())
    }

    private fun handleError(ex: Throwable?) {
        android.util.Log.i("z- error", ex?.printStackTrace().toString())
        android.widget.Toast.makeText(this, ex?.message ?: "Unknown error" , android.widget.Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
