package com.example.learning.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun DHList() {
    ConstraintLayout {
        val (header, columnList) = createRefs()
        DockableHeader(
            modifier = Modifier.constrainAs(header){
                top.linkTo(parent.top)
            }.fillMaxWidth()
        )
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier
                .constrainAs(columnList) {
                    top.linkTo(header.bottom)
                }
                .fillMaxWidth(),
        ) {
            items(10) {
                Box(Modifier.fillMaxWidth().height(100.dp).background(Color.LightGray))
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun DockableHeader(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Text("hello", Modifier.height(100.dp))
    }
}

@Preview(showBackground = true, widthDp = 480, heightDp = 768)
@Composable
fun PreviewDHList() {
    DHList()
}