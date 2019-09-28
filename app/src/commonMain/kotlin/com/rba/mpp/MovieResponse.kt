package com.rba.mpp

import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val results: List<MovieItemResponse>
)