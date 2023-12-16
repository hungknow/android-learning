package com.example.jetpackex.models

import androidx.annotation.DrawableRes

data class Post(
    val id: String,
    val title: String,
    val subtitle: String? = null,
//    val publication: Publication? = null,
    val metadata: Metadata,
    @DrawableRes val imageId: Int,
    @DrawableRes val imageThumbId: Int,
)

data class Metadata (
    val author: PostAuthor,
    val date: String,
    val readTimeMinutes: Int,
)

data class PostAuthor(
    val name: String,
    val url: String? = null,
)
