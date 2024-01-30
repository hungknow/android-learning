package com.example.learning.models

data class PostsFeed(
    val highlighted: Post,
    val recommendedPosts: List<Post>,
) {

}