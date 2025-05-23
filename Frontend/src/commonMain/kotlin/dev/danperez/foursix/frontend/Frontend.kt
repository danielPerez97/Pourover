@file:OptIn(ExperimentalMaterial3Api::class)

package dev.danperez.foursix.frontend

import PourChronology
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.danperez.foursix.presenter.FourSixEvent
import dev.danperez.foursix.presenter.FourSixState
import dev.danperez.foursix.presenter.Strength
import dev.danperez.foursix.presenter.Sweetness

@Composable
fun Screen(
    model: FourSixState,
    onEvent: (FourSixEvent) -> Unit,
    modifier: Modifier = Modifier,
)
{
    var gramsState by remember { mutableStateOf(model.grams.toString()) }
    Scaffold(
        modifier.imePadding(),
        topBar = {
            TopAppBar(
                title = { Text("Pourover") }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text("For ${model.grams} grams of coffee...")
            HorizontalDivider(Modifier.padding(vertical = 4.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                PoursData(
                    firstHalfPours = model.firstHalfPours,
                    secondHalfPours = model.secondHalfPours,
                    modifier = Modifier.fillMaxWidth()
                        .weight(1f)
                )

                ControllerView(
                    model = model,
                    onEvent = onEvent,
                    gramsState = gramsState,
                    onGramsChanged = { gramsState = it },
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
fun PoursData(
    firstHalfPours: List<Int>,
    secondHalfPours: List<Int>,
    modifier: Modifier = Modifier,
)
{
    Box(modifier = modifier) {
        if(firstHalfPours.isNotEmpty() && secondHalfPours.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
            ) {
                PoursList(
                    firstHalfPours,
                    secondHalfPours,
                    Modifier.fillMaxWidth(0.5f),
                )
                PourChronology(
                    allPours = firstHalfPours + secondHalfPours,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            Text(
                text = "Please enter your beans in grams.",
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Composable
fun ControllerView(
    model: FourSixState,
    onEvent: (FourSixEvent) -> Unit,
    gramsState: String,
    onGramsChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pattern = remember { Regex("^\\d+\$") }

    Column(modifier.fillMaxWidth()) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
        ) {
            Row(Modifier.fillMaxWidth()) {
                Column(
                    Modifier.fillMaxWidth(0.5f)
                ) {
                    Text("Choose Sweetness:", fontWeight = FontWeight.Bold)
                    SweetnessRadioButtonGroup(
                        selectedSweetness = model.sweetness,
                        onEvent = onEvent,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Column(
                    Modifier.fillMaxWidth()
                ) {
                    Text("Choose Strength:", fontWeight = FontWeight.Bold)
                    StrengthRadioGroup(
                        selectedStrength = model.strength,
                        onEvent = onEvent,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

        }
        val focusManager = LocalFocusManager.current
        TextField(
            value = gramsState,
            label = { Text("Beans(g)") },
            trailingIcon = {
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                    },
                ) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Close Keyboard")
                }
            },
            onValueChange = {
                onGramsChanged(it)
                if(it.isEmpty() || it.matches(pattern)) {
                    onEvent(FourSixEvent.GramsChanged(it))
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions (
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PoursList(
    firstHalfPours: List<Int>,
    secondHalfPours: List<Int>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        if(firstHalfPours.isNotEmpty()) {
            item {
                Text("First 40%(Sweetness)")
            }
            items(firstHalfPours) {
                Text("$it(g)")
            }
        }

        if(firstHalfPours.isNotEmpty() && secondHalfPours.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        if(secondHalfPours.isNotEmpty()) {
            item {
                Text("Second 60%(Strength)")
            }
            items(secondHalfPours) {
                Text("$it(g)")
            }
        }
    }
}

@Composable
fun StrengthRadioGroup(
    selectedStrength: Strength,
    onEvent: (FourSixEvent.StrengthChanged) -> Unit,
    modifier: Modifier = Modifier,
)
{
    Column(modifier = modifier) {
        // Create a row for each strength option with a label and a radio button
        Strength.values().forEach { strength ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    text = when (strength) {
                        Strength.Lighter -> "Lighter"
                        Strength.Stronger -> "Stronger"
                        Strength.EvenStronger -> "Even Stronger"
                    },
                    modifier = Modifier.weight(1f)
                )
                RadioButton(
                    selected = strength == selectedStrength,
                    onClick = { onEvent(FourSixEvent.StrengthChanged(strength)) }
                )
            }
        }
    }
}

@Composable
fun SweetnessRadioButtonGroup(
    selectedSweetness: Sweetness,
    onEvent: (FourSixEvent.SweetnessChanged) -> Unit,
    modifier: Modifier = Modifier
)
{
    Column(modifier = modifier) {
        // Create a row for each strength option with a label and a radio button
        Sweetness.values().forEach { sweetness ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    text = when (sweetness) {
                        Sweetness.Standard -> "Standard"
                        Sweetness.Sweeter -> "Sweeter"
                        Sweetness.Brighter -> "Brighter"
                    },
                    modifier = Modifier.weight(1f)
                )
                RadioButton(
                    selected = sweetness == selectedSweetness,
                    onClick = { onEvent(FourSixEvent.SweetnessChanged(sweetness)) }
                )
            }
        }
    }
}