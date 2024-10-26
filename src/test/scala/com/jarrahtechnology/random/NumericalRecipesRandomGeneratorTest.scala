package com.jarrahtechnology.random

import org.junit.Test
import junit.framework.TestCase
import org.junit.Assert.*

class NumericalRecipesRandomGeneratorTest extends TestCase {

    @Test def testBasic: Unit =  { 
        val seed = 27
        val genRandom = new NumericalRecipesRandomGenerator(seed)

        assertEquals(0.350355267222205d, genRandom.next01(), 0.000000000000001d)
        assertEquals(0.0635706177277354d, genRandom.next01(), 0.000000000000001d)
        assertEquals(0.453957301310244d, genRandom.next01(), 0.000000000000001d)
        assertEquals(0.248034119255857d, genRandom.next01(), 0.000000000000001d)
        assertEquals(0.101590623195092d, genRandom.next01(), 0.000000000000001d)
        assertEquals(0.330550788124348d, genRandom.next01(), 0.000000000000001d)
        assertEquals(0.982479072633422d, genRandom.next01(), 0.000000000000001d)
        assertEquals(0.895272965494205d, genRandom.next01(), 0.000000000000001d)
        assertEquals(0.430665487158422d, genRandom.next01(), 0.000000000000001d)
        assertEquals(0.805694462641d, genRandom.next01(), 0.000000000000001d)
    }
}