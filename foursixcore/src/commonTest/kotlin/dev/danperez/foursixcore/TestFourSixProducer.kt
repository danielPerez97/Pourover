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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestFourSixProducer
{

    private val producer = FourSixProducer()

    @Test
    fun testWaterRatio()
    {
        assertTrue { producer.getWaterGrams(15, 15) == 225 }
        assertTrue { producer.getWaterGrams(20, 15) == 300 }
    }

    @Test
    fun testStandardSweetness()
    {
        val sweetness = producer.calculateSweetness(120, FourSixProducer.Sweetness.Standard)
        sweetness.forEach { grams ->
            assertEquals(60, grams)
        }
    }

    @Test
    fun testSweeterSweetness()
    {
        val sweetness = producer.calculateSweetness(120, FourSixProducer.Sweetness.Sweeter)
        sweetness.forEachIndexed { index: Int, grams ->
            if(index == 0) {
                assertEquals(50, grams)
            } else
            {
                assertEquals(70, grams)
            }
        }
    }

    @Test
    fun testBrighterSweetness()
    {
        val sweetness = producer.calculateSweetness(120, FourSixProducer.Sweetness.Brighter)
        sweetness.forEachIndexed { index: Int, grams ->
            if(index == 0) {
                assertEquals(70, grams)
            } else
            {
                assertEquals(50, grams)
            }
        }
    }

    @Test
    fun testLightStrength()
    {
        val lightCoffee = producer.calculateStrength(180, FourSixProducer.Strength.Lighter)
        assertEquals(1, lightCoffee.size)
        assertEquals(180, lightCoffee.first())
    }

    @Test
    fun testStrongStrength()
    {
        val strongCoffee = producer.calculateStrength(180, FourSixProducer.Strength.Stronger)
        assertEquals(2, strongCoffee.size)
        strongCoffee.forEach { grams ->
            assertEquals(90, grams)
        }
    }

    @Test
    fun testEvenStrongerStrength()
    {
        val strongestCoffee = producer.calculateStrength(180, FourSixProducer.Strength.EvenStronger)
        assertEquals(3, strongestCoffee.size)
        strongestCoffee.forEach { grams ->
            assertEquals(60, grams)
        }
    }

    @Test
    fun testStandardLightStrengthPour()
    {
        val standardStrongPour = producer.calculate(
            gramsBeans = 20,
            sweetness = FourSixProducer.Sweetness.Standard,
            strength = FourSixProducer.Strength.Lighter,
        )

        assertEquals(2, standardStrongPour.first.size)
        assertEquals(1, standardStrongPour.second.size)

        standardStrongPour.first.forEach { grams ->
            assertEquals(60, grams)
        }

        standardStrongPour.second.forEach { grams ->
            assertEquals(180, grams)
        }
    }

    @Test
    fun testStandardStrongPour()
    {
        val standardStrongPour = producer.calculate(
            gramsBeans = 20,
            sweetness = FourSixProducer.Sweetness.Standard,
            strength = FourSixProducer.Strength.Stronger
        )

        assertEquals(2, standardStrongPour.first.size)
        assertEquals(2, standardStrongPour.second.size)

        standardStrongPour.first.forEach { grams ->
            assertEquals(60, grams)
        }

        standardStrongPour.second.forEach { grams ->
            assertEquals(90, grams)
        }
    }

    @Test
    fun testStandardEvenStrongerPour()
    {
        val standardStrongPour = producer.calculate(
            gramsBeans = 20,
            sweetness = FourSixProducer.Sweetness.Standard,
            strength = FourSixProducer.Strength.EvenStronger
        )

        assertEquals(2, standardStrongPour.first.size)
        assertEquals(3, standardStrongPour.second.size)

        standardStrongPour.first.forEach { grams ->
            assertEquals(60, grams)
        }

        standardStrongPour.second.forEach { grams ->
            assertEquals(60, grams)
        }
    }
}