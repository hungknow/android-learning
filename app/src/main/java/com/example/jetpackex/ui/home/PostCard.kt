package com.example.jetpackex.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackex.R
import com.example.jetpackex.data.post1
import com.example.jetpackex.models.Post
import com.example.jetpackex.ui.BookmarkButton

@Composable
fun PostCardSimple(post: Post) {
    Row {
        Image(
            modifier = Modifier.padding(16.dp).size(40.dp, 40.dp),
            painter = painterResource(post.imageThumbId),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3,
                softWrap = true,
            )
            Row {
                Text(
                    text = stringResource(
                        id = R.string.home_post_min_read,
                        formatArgs = arrayOf(
                            post.metadata.author.name,
                            post.metadata.readTimeMinutes,
                        )
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        BookmarkButton(
            isBookmarked = false
        )
    }
}

@Preview
@Composable
fun PreviewPostCardSimple() {
    Surface {
        PostCardSimple(post = post1)
    }
}