package com.example.learning.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Element(modifier: Modifier = Modifier) {
    Box(
        modifier
            .height(30.dp)
            .fillMaxWidth()
            .background(Color.Gray)
    ) {}
}

@Composable
fun LayoutModifier() {
    Column(Modifier.padding(horizontal = 8.dp, vertical = 20.dp)) {
        Element()
        Spacer(Modifier.height(10.dp))
        Element(modifier = Modifier.layout { measurable, constraints ->
            val placeable = measurable.measure(
                constraints.copy(
                    maxWidth = constraints.maxWidth + 20.dp.roundToPx()
                )
            )
            layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        })
        Spacer(Modifier.height(10.dp))
        Element()
        Spacer(Modifier.height(10.dp))
        Element()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLayoutModifier() {
    LayoutModifier()
}

@Composable
fun BasicColumn(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placables = measurables.map {
            it.measure(constraints.copy(minHeight = 0))
        }

        var layoutHeight = 0
        placables.forEach {
            layoutHeight += it.height
        }

        layout(constraints.maxWidth, layoutHeight) {
            var yPosition = 0
            placables.forEach {
                it.placeRelative(0, yPosition)
                yPosition += it.height
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBasicColumn() {
    BasicColumn() {
        Text("hello")
        Text("How are you")
    }
}

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp,
) = layout { measurables, constraints ->
    val placeable = measurables.measure(constraints)
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val placeableY = firstBaselineToTop.roundToPx() - placeable.height
    val height = placeable.height + placeableY
    layout(placeable.width, height) {
        placeable.placeRelative(0, placeableY)
    }
}

@Preview(showBackground = true)
@Composable
private fun TextWithPaddingToBaseline() {
    MaterialTheme {
        Column {
            Text(text = "Hi there!", modifier = Modifier.firstBaselineToTop(32.dp))
            Text(
                text = "Hi there!",
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(top = 32.dp)
            )
        }
    }
}