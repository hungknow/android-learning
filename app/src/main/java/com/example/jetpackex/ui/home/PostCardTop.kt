package com.example.jetpackex.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackex.R
import com.example.jetpackex.data.post1
import com.example.jetpackex.models.Post

@Composable
fun PostCardTop(
    post: Post
) {
    val typography = MaterialTheme.typography
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Image(
            painter = painterResource(id = post.imageId),
            contentDescription = null,
            modifier = Modifier
                .heightIn(min = 180.dp)
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = post.title,
            style = typography.titleLarge
        )
        Text(
            text = post.metadata.author.name,
            style = typography.labelLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = stringResource(
                id = R.string.home_post_min_read,
                formatArgs = arrayOf(
                    post.metadata.date,
                    post.metadata.readTimeMinutes
                )
            ),
            style = typography.bodySmall
        )

    }
}

@Preview
@Composable
fun PreviewPostCardTop() {
    PostCardTop(post = post1)
}
