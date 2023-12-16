package com.example.jetpackex.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackex.data.post1
import com.example.jetpackex.ui.home.PostCardSimple

@Composable
fun BookmarkButton(
    isBookmarked: Boolean
) {
    IconToggleButton(
//            onClick = fun() {},
        checked = false,
        onCheckedChange = {},
        modifier = Modifier
            .width(50.dp)
            .padding(vertical = 2.dp, horizontal = 6.dp)
    ) {
        Icon(
            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
            contentDescription = null // handled by click label of parent
        )
    }
}

@Preview
@Composable
fun PreviewBookmarkButton() {
    Surface {
        BookmarkButton(isBookmarked = false)
    }
}