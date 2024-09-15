package dev.danperez.foursix.views

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.danperez.foursix.FourSixApplication
import dev.danperez.foursix.views.theme.FourSixTheme
import dev.marcellogalhardo.retained.activity.retain
import javax.inject.Inject

class FourSixActivity: AppCompatActivity()
{
    @Inject lateinit var presenterFactory: FourSixPresenter.Factory
    private val presenter: FourSixPresenter by retain {
        presenterFactory.create(it.scope, AndroidUiDispatcher.Main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as FourSixApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            val state by presenter.models.collectAsState()

            FourSixTheme {
                Screen(
                    model = state,
                    onEvent = { presenter.take(it) },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
fun Screen(
    model: FourSixState,
    onEvent: (FourSixEvent) -> Unit,
    modifier: Modifier = Modifier,
)
{
    Surface(modifier) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize(),
        ) {
            PoursData(
                firstHalfPours = model.firstHalfPours,
                secondHalfPours = model.secondHalfPours,
                modifier = Modifier.fillMaxWidth()
            )

            ControllerView(
                model = model,
                onEvent = onEvent,
                modifier = Modifier
            )
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
                modifier = modifier
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
    modifier: Modifier = Modifier
) {
    val pattern = remember { Regex("^\\d+\$") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
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
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
        TextField(
            value = model.grams,
            label = { Text("Beans(Grams)") },
            onValueChange = {
                if(it.text.isEmpty() || it.text.matches(pattern)) {
                    onEvent(FourSixEvent.GramsChanged(it))
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                Text("$it grams")
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
                Text("$it grams")
            }
        }
    }
}

enum class Strength {
    /** Lighter strength profile: Uses the entire 60% of water in a single pour. */
    Lighter,

    /** Stronger profile: Splits the 60% of water into two equal pours. */
    Stronger,

    /** Even stronger profile: Splits the 60% of water into three equal pours. */
    EvenStronger
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

enum class Sweetness {
    /** Standard sweetness profile: Splits the first 40% of water into two equal pours. */
    Standard,

    /** Sweeter profile: Uses 41.67% of the total water for the first pour. */
    Sweeter,

    /** Brighter profile: Uses 58.33% of the total water for the first pour. */
    Brighter;
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