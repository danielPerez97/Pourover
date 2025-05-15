import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PourChronology(
    allPours: List<Int>,
    modifier: Modifier = Modifier
)
{
// Calculate the cumulative sum for each pour step
    val cumulativePours = remember(allPours) { allPours.runningFold(0) { acc, pour -> acc + pour } }

    LazyColumn(modifier = modifier) {
        itemsIndexed(cumulativePours) { index: Int, total ->
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