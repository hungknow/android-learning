package com.example.learning.ui.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

fun Color.Companion.random(): Color {
    val defaultGray = Color.Gray
    val randomInt = 0.0f + (Random.nextFloat() * 0.4f)
    return Color(
        defaultGray.red * (1f - randomInt),
        defaultGray.green * (1f - randomInt),
        defaultGray.blue * (1f - randomInt),
    )
}

@Composable
fun ConstraintLayoutUse() {
    ConstraintLayout {
        val (button, text) = createRefs()
        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button")
        }
        Text(
            "Text",
            Modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = 16.dp)
            }
        )
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        val topBarrier = createTopBarrier(button, text)

        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}

@Composable
fun DecoupleConstraintLayoutUse() {
    BoxWithConstraints {
        val constraints = if (minWidth < 600.dp) {
            decoupledConstraints(margin = 16.dp) // Portrait constraints
        } else {
            decoupledConstraints(margin = 32.dp) // Landscape constraints
        }
        ConstraintLayout(constraints) {
            Button(
                onClick = {},
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }
            Text("Text", Modifier.layoutId("text"))
        }
    }
}

@Preview
@Composable
fun PreviewConstraintLayoutUse() {
    ConstraintLayoutUse()
}

@Preview
@Composable
fun PreviewDecoupleConstraintLayoutUse() {
    DecoupleConstraintLayoutUse()
}

val MinChartValue = HorizontalAlignmentLine(merger = { old, new -> max(old, new) })
var MaxChartValue = HorizontalAlignmentLine(merger = { old, new -> min(old, new) })

@Composable
fun BarChart(
    dataPoints: List<Int>,
    modifier: Modifier = Modifier,
    drawSlotEdge: Boolean = false,
) {
    val maxValue = remember(dataPoints) { dataPoints.maxOrNull()!! * 1.2f }

    BoxWithConstraints(modifier) {
        val density = LocalDensity.current
        val rectWidth = 60f
        with(density) {
            val yPositionRatio = remember(density, maxHeight, maxValue) {
                maxHeight.toPx() / maxValue
            }
            val barWidth = remember(density, maxWidth, dataPoints.size) {
                maxWidth.toPx() / (dataPoints.size + 0)
            }
            // Display the bar at the center of its slot
            val xOffset = remember(barWidth) {
                (barWidth / 2) - (rectWidth / 2)
            }
            var maxYBaseline = remember(dataPoints) {
                dataPoints.maxOrNull()?.let {
                    (maxValue - it) * yPositionRatio
                } ?: 0f
            }
            var minYBaseline = remember(dataPoints) {
                dataPoints.minOrNull()?.let {
                    (maxValue - it) * yPositionRatio
                } ?: 0f
            }
            Layout(
                content = {},
                modifier = Modifier.drawBehind {
                    if (drawSlotEdge) {
                        dataPoints.forEachIndexed { index, _ ->
                            drawLine(
                                Color.Gray,
                                start = Offset((index + 1) * barWidth, 0f),
                                end = Offset((index + 1) * barWidth, maxHeight.toPx()),
                                strokeWidth = 5f
                            )
                        }
                    }

                    dataPoints.forEachIndexed { index, dataPoint ->
                        val rectSize = Size(rectWidth, dataPoint * yPositionRatio)
                        val topLeftOffset = Offset(
                            x = barWidth * (index) + xOffset,
                            y = yPositionRatio * (maxValue - dataPoint)
                        )
                        drawRect(Color(0xFF3DDC84), topLeftOffset, rectSize)
                    }
                    drawLine(
                        Color(0xFF073042),
                        start = Offset(0f, 0f),
                        end = Offset(0f, maxHeight.toPx()),
                        strokeWidth = 6f
                    )
                    drawLine(
                        Color(0xFF073042),
                        start = Offset(0f, maxHeight.toPx()),
                        end = Offset(maxWidth.toPx(), maxHeight.toPx()),
                        strokeWidth = 6f
                    )
                }
            ) { _, constraints ->
                with(constraints) {
                    layout(
                        width = if (hasBoundedWidth) maxWidth else minWidth,
                        height = if (hasBoundedHeight) maxHeight else minHeight,
                        // Custom AlignmentLines are set here. These are propagated
                        // to direct and indirect parent composables.
                        alignmentLines = mapOf(
                            MinChartValue to minYBaseline.toInt(),
                            MaxChartValue to maxYBaseline.toInt(),
                        )
                    ) {}
                }
            }
        }
    }
}

@Composable
fun BarChartMinMax(
    dataPoints: List<Int>,
    maxText: @Composable () -> Unit,
    minText: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val maxAxisWidth = 150
    Layout(
        content = {
            maxText()
            minText()
            BarChart(
                dataPoints = dataPoints,
                drawSlotEdge = true
            )
        },
        modifier = modifier
    ) { measurables, constraints ->
        check(measurables.size == 3)
        val textMeasurables = listOf(measurables[0], measurables[1])
        val textPlaceables = textMeasurables.map {
            it.measure(
                constraints.copy(
                    minWidth = 0,
                    minHeight = 0,
                    maxWidth = maxAxisWidth
                )
            )
        }

        // Measure the width of text first
        val maxTextPlaceable = textPlaceables[0]
        val minTextPlaceable = textPlaceables[1]

        val barWidth = min(
            maxAxisWidth, max(
                maxTextPlaceable.width,
                maxTextPlaceable.width
            )
        )

        // Then measure the bar chart by subtracting the width of left bar
        val barChartPlaceable = measurables[2].measure(
            constraints.copy(
                minWidth = 0,
                minHeight = 0,
                maxWidth = constraints.maxWidth - barWidth - 20
            )
        )

        val minValueBaseline = barChartPlaceable[MinChartValue]
        val maxBaseAlignment = barChartPlaceable[MaxChartValue]
        layout(
            constraints.maxWidth,
            constraints.maxHeight,
        ) {
            minTextPlaceable.placeRelative(
                x = 0,
                y = minValueBaseline - (minTextPlaceable.height / 2)
            )
            maxTextPlaceable.placeRelative(
                x = 0,
                y = maxBaseAlignment - (maxTextPlaceable.height / 2)
            )
            barChartPlaceable.placeRelative(
                x = barWidth + 20,
                y = 0
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewBarChart() {
    val dataPoints = listOf(4, 24, 15)
    MaterialTheme {
        BarChartMinMax(
            dataPoints,
            { Text("Max") },
            { Text("Min") },
            modifier = Modifier.padding(16.dp)
        )
    }
}