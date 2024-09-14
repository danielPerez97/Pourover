/*
 * Copyright 2016 Daniel Perez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.danperez.foursixcore

import kotlin.math.roundToInt

class FourSixProducer {

    /**
     * Calculates the water distribution for brewing coffee using the 4:6 method.
     *
     * @param gramsBeans The amount of coffee beans in grams.
     * @param sweetness The desired sweetness profile (Standard, Sweeter, Brighter).
     * @param strength The desired strength profile (Lighter, Stronger, EvenStronger).
     * @param waterRatio The water-to-coffee ratio (default is 15).
     * @return A pair of lists containing the distribution of water pours. The first list represents the
     *         sweetness half (40% of the water), and the second list represents the strength half (60% of the water).
     */
    fun calculate(
        gramsBeans: Int,
        sweetness: Sweetness = Sweetness.Standard,
        strength: Strength = Strength.Stronger,
        waterRatio: Int = 15,
    ): Pair<List<Int>, List<Int>> {
        val totalWaterGrams = getWaterGrams(gramsBeans, waterRatio)
        val totalFirstHalfGrams = totalWaterGrams * 0.4
        val totalSecondHalfGrams = totalWaterGrams * 0.6

        val firstHalfGramsList = calculateSweetness(totalFirstHalfGrams.roundToInt(), sweetness)
        val secondHalfGramsList = calculateStrength(totalSecondHalfGrams.roundToInt(), strength)

        return Pair(firstHalfGramsList, secondHalfGramsList)
    }

    /**
     * Calculates the water distribution for the "sweetness" half of the brewing process.
     * This method assumes the input represents 40% of the total water amount.
     *
     * @param gramsWater The amount of water in grams to be distributed for sweetness.
     * @param sweetness The desired sweetness profile (Standard, Sweeter, Brighter).
     * @return A list containing the distribution of water pours for the sweetness phase.
     *         The list will always contain two elements as the first half of the water is split into two pours.
     */
    internal fun calculateSweetness(gramsWater: Int, sweetness: Sweetness): List<Int> {
        return when (sweetness) {
            Sweetness.Standard -> List(2) {
                return@List (gramsWater * 0.5).roundToInt()
            }

            Sweetness.Sweeter -> List(2) { index ->
                val first = 0.4167
                if (index == 0) {
                    return@List (gramsWater * first).roundToInt()
                }
                // Use the remaining water for second half of the pour
                return@List gramsWater - (gramsWater * first).roundToInt()
            }

            Sweetness.Brighter -> List(2) { index ->
                val first = 0.5833
                if (index == 0) {
                    return@List (gramsWater * first).roundToInt()
                }
                // Use the remaining water for second half of the pour
                return@List gramsWater - (gramsWater * first).roundToInt()
            }
        }
    }

    /**
     * Calculates the water distribution for the "strength" half of the brewing process.
     * This method assumes the input represents 60% of the total water amount.
     *
     * @param gramsWater The amount of water in grams to be distributed for strength.
     * @param strength The desired strength profile (Lighter, Stronger, EvenStronger).
     * @return A list containing the distribution of water pours for the strength phase.
     *         The number of elements in the list varies depending on the strength parameter.
     */
    internal fun calculateStrength(gramsWater: Int, strength: Strength): List<Int> {
        return when (strength) {
            Strength.Lighter -> List(1) { gramsWater / 1 }
            Strength.Stronger -> List(2) { gramsWater / 2 }
            Strength.EvenStronger -> List(3) { gramsWater / 3 }
        }
    }

    /**
     * Calculates the total amount of water needed for the brew based on the given coffee-to-water ratio.
     *
     * @param gramsBeans The amount of coffee beans in grams.
     * @param waterRatio The water-to-coffee ratio.
     * @return The total amount of water in grams.
     */
    internal fun getWaterGrams(gramsBeans: Int, waterRatio: Int): Int {
        return gramsBeans * waterRatio
    }

    /**
     * Represents the sweetness profile for the coffee brewing process using the 4:6 method.
     * Each option adjusts the distribution of water in the initial phase (40% of total water).
     *
     * - Standard: Splits the first 40% of water into two equal pours.
     * - Sweeter: Uses 41.67% of the total water for the first pour, and the remaining water for the second pour.
     * - Brighter: Uses 58.33% of the total water for the first pour, and the remaining water for the second pour.
     */
    enum class Sweetness {
        /** Standard sweetness profile: Splits the first 40% of water into two equal pours. */
        Standard,

        /** Sweeter profile: Uses 41.67% of the total water for the first pour. */
        Sweeter,

        /** Brighter profile: Uses 58.33% of the total water for the first pour. */
        Brighter
    }

    /**
     * Represents the strength profile for the coffee brewing process using the 4:6 method.
     * Each option adjusts how the remaining 60% of the water is distributed.
     *
     * - Lighter: Uses the entire 60% in a single pour.
     * - Stronger: Splits the 60% into two equal pours.
     * - EvenStronger: Splits the 60% into three equal pours.
     */
    enum class Strength {
        /** Lighter strength profile: Uses the entire 60% of water in a single pour. */
        Lighter,

        /** Stronger profile: Splits the 60% of water into two equal pours. */
        Stronger,

        /** Even stronger profile: Splits the 60% of water into three equal pours. */
        EvenStronger
    }
}
