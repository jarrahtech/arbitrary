package com.jarrahtechnology.random

import org.junit.Test
import junit.framework.TestCase
import org.junit.Assert.*

class JavaRandomGeneratorTest extends TestCase {

    @Test def testBasic: Unit =  { 
        val seed = 26
        val javaRandom = java.util.Random(seed)
        val genRandom = new JavaRandomGenerator(seed)

        for (i <- 0 to 1000) {
            assertEquals(s"error at #$i ->", javaRandom.nextDouble(), genRandom.next01(), 0.0000000001d)
        }
    }
}