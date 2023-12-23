package com.example.jetpackex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowSimpleUsageExample() {
    val rows = 4
    val columns = 3
    Column {
        FlowRow(modifier = Modifier.padding(8.dp)) {
            AssistChip(
                onClick = {},
                label = { Text("Price: High to Low") })
            AssistChip(
                onClick = {},
                label = { Text("Avg rating: 4+") })
            AssistChip(
                onClick = {},
                label = { Text("Avg rating: 4+") })
            AssistChip(
                onClick = {},
                label = { Text("Sample") })
            AssistChip(
                onClick = {},
                label = { Text("This is the example text") })
            AssistChip(
                onClick = {},
                label = { Text("Avg rating: 4+") })
            AssistChip(
                onClick = {},
                label = { Text("Avg rating: 4+") })
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            maxItemsInEachRow = 4
        ) {
            val itemModifier = Modifier
                .padding(4.dp)
                .height(80.dp)
                .background(Color.Gray)
            repeat(rows * columns) {
                if ((it + 1) % 3 == 0) {
                    Spacer(modifier = itemModifier.fillMaxWidth())
                } else {
                    Spacer(modifier = itemModifier.weight(0.5f))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFlowRowSimpleUsageExample() {
    FlowRowSimpleUsageExample()
}