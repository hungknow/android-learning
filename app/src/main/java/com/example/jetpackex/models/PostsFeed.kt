package com.example.jetpackex.models

data class PostsFeed(
    val highlighted: Post,
    val recommendedPosts: List<Post>,
) {

}