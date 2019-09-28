package com.rba.mpp

import com.rba.mpp.shared.ApplicationDispatcher
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json

class MovieApi {

    private val httpClient = HttpClient()

    fun getMovieList(success: (List<MovieItemResponse>) -> Unit, failure: (Throwable?) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val url =
                    "https://api.themoviedb.org/3/movie/popular?api_key=203f0adce5b630039ff0783be7e0718f&language=en-US"
                val json = httpClient.get<String>(url)
                Json.nonstrict.parse(MovieResponse.serializer(), json)
                    .results
                    .also(success)
            } catch (ex: Exception) {
                failure(ex)
            }
        }
    }
}