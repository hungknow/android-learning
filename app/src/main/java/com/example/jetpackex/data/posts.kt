package com.example.jetpackex.data

import com.example.jetpackex.R
import com.example.jetpackex.models.Metadata
import com.example.jetpackex.models.Post
import com.example.jetpackex.models.PostAuthor
import com.example.jetpackex.models.PostsFeed

val pietro = PostAuthor("Pietro Maggi", "https://medium.com/@pmaggi")
val manuel = PostAuthor("Manuel Vivo", "https://medium.com/@manuelvicnt")
val florina = PostAuthor(
    "Florina Muntenescu",
    "https://medium.com/@florina.muntenescu"
)
val jose =
    PostAuthor("Jose Alcérreca", "https://medium.com/@JoseAlcerreca")

val post1 = Post(
    id = "post_1",
    title = "A Little Thing about Android Module Paths",
    subtitle = "How to configure your module paths, instead of using Gradle’s default.",
    metadata = Metadata(
        author = pietro,
        date = "August 02",
        readTimeMinutes = 1
    ),
    imageId = R.drawable.post_1,
    imageThumbId = R.drawable.post_1_thumb,
)

val post2 = Post(
    id = "7446d8dfd7dc",
    title = "Dagger in Kotlin: Gotchas and Optimizations",
    subtitle = "Use Dagger in Kotlin! This article includes best practices to optimize your build time and gotchas you might encounter.",
//    url = "https://medium.com/androiddevelopers/dagger-in-kotlin-gotchas-and-optimizations-7446d8dfd7dc",
//    publication = publication,
    metadata = Metadata(
        author = manuel,
        date = "July 30",
        readTimeMinutes = 3
    ),
//    paragraphs = paragraphsPost2,
    imageId = R.drawable.post_2,
    imageThumbId = R.drawable.post_2_thumb
)

val post4 = Post(
    id = "84eb677660d9",
    title = "Locale changes and the AndroidViewModel antipattern",
    subtitle = "TL;DR: Expose resource IDs from ViewModels to avoid showing obsolete data.",
//    url = "https://medium.com/androiddevelopers/locale-changes-and-the-androidviewmodel-antipattern-84eb677660d9",
//    publication = publication,
    metadata = Metadata(
        author = jose,
        date = "April 02",
        readTimeMinutes = 1
    ),
//    paragraphs = paragraphsPost4,
    imageId = R.drawable.post_4,
    imageThumbId = R.drawable.post_4_thumb
)

val postsFeed = PostsFeed(
    highlighted = post4,
    recommendedPosts = listOf(post1, post2)
)