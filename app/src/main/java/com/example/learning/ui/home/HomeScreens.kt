package com.example.learning.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learning.R
import com.example.learning.data.postsFeed
import com.example.learning.models.Post

@Composable
fun HomeScreen(
    uiState: HomeUiState.HasPosts,
    state: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        state = state
    ) {
        item {
            Box(
                Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Search")
            }
        }
        item {
            PostListTopSection(uiState.postsFeed.highlighted)
            PostListDivider()
        }
        item {
            PostListSimpleSection(uiState.postsFeed.recommendedPosts)
            PostListDivider()
        }
        item {
            PostListPopularSection(uiState.postsFeed.recommendedPosts)
        }
    }
}

@Composable
private fun PostListPopularSection(
    posts: List<Post>
) {
    Column {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Popular on Jetnews",
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .height(IntrinsicSize.Max)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (post in posts) {
                PostCardPopular(post)
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun PostListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    )
}

@Composable
fun PostListTopSection(topPost: Post) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        text = stringResource(id = R.string.home_top_section_title),
        style = MaterialTheme.typography.titleMedium,
    )
    PostCardTop(post = topPost)
}

@Composable
fun PostListSimpleSection(posts: List<Post>) {
    Column {
        posts.forEach { post ->
            PostCardSimple(
                post = post
            )
        }
    }
}

@Composable
fun PostCardPopular(
    post: Post,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(280.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column {
            Image(
                painter = painterResource(post.imageId),
                contentDescription = null, // decorative
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = post.metadata.author.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(
                        id = R.string.home_post_min_read,
                        formatArgs = arrayOf(
                            post.metadata.date,
                            post.metadata.readTimeMinutes
                        )
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(device = Devices.PIXEL_4)
@Preview(device = Devices.TABLET)
@Composable
fun PreviewHome() {
    HomeScreen(
        HomeUiState.HasPosts(
            postsFeed = postsFeed,
            isLoading = false,
            errorMessages = emptyList(),
            searchInput = ""
        )
    )
}

@Preview
@Composable
fun PreviewPostListPopularSection() {
    Surface {
        PostListPopularSection(posts = postsFeed.recommendedPosts)
    }
}