package com.jarrahtechnology.random

import org.junit.Test
import junit.framework.TestCase
import org.junit.Assert.*

class RandomGeneratorTest extends TestCase {

    @Test def testBasic: Unit =  { 
        assertEquals("0->0", 0, RandomGenerator.shift01(0), 0.0000000001d)
        assertEquals("Max->1", 0.999999999d, RandomGenerator.shift01(Int.MaxValue), 0.000000001d)
        assertTrue("Max<1", RandomGenerator.shift01(Int.MaxValue)<1)
        assertEquals("Max/2->0.5", 0.5d, RandomGenerator.shift01(Int.MaxValue/2), 0.000000001d)
        assertEquals("Min->0", 0d, RandomGenerator.shift01(Int.MinValue), 0.000000001d)
        assertTrue("Min>=0", RandomGenerator.shift01(Int.MinValue)>=0)
        assertEquals("Min/2->0.5", 0.5d, RandomGenerator.shift01(Int.MinValue/2), 0.000000001d)
        assertEquals("-1->0.9r", 0.999999999d, RandomGenerator.shift01(-1), 0.000000001d)
    }
}