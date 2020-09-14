package com.sameer.katapp.model

import com.squareup.moshi.Json

data class CatImage(val id: String, @Json(name = "url") val imageUrl: String, val width: Int, val height: Int)