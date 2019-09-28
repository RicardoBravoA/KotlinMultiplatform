package com.rba.mpp

import kotlinx.serialization.Serializable

@Serializable
data class MovieItemResponse(
    val id: Int,
    val title: String,
    val poster_path: String
)