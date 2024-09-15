package dev.danperez.foursix.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PourChronology(
    allPours: List<Int>,
    modifier: Modifier = Modifier
)
{
// Calculate the cumulative sum for each pour step
    val cumulativePours = allPours.runningFold(0) { acc, pour -> acc + pour }

    Column(modifier = modifier) {
        cumulativePours.forEachIndexed { index, total ->
            if (index > 0) {
                // Show downward arrow for each pour after the first entry
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Arrow Down",
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            // Display the pour label and cumulative total
            Text(
                text = if (index == 0) "Starting Water: $total(g)" else "Pour ${index}: $total(g)",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
@Preview
fun PourChronologyPreview()
{
    val pours = listOf(45, 45, 45, 45, 45)
    PourChronology(pours, Modifier.fillMaxSize())
}