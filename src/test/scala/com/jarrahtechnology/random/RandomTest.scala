package com.jarrahtechnology.random

import org.junit.Test
import junit.framework.TestCase
import org.junit.Assert.*

import scala.language.implicitConversions
import com.jarrahtechnology.util.{Vector2, Vector3}

class RandomTest extends TestCase {

  @Test def testBasic: Unit = {
    val rand1 = Random.javaWithSeed(1)
    assertEquals("seed 1", rand1.seed, 1)
    assertEquals(rand1.nextNaturalUpto(6), 4)
    assertEquals(rand1.nextNaturalUpto(6), 2)
    assertEquals(rand1.nextNaturalUpto(6), 1)

    val rand2 = Random.javaRandomSeed()
    assertTrue("seed 2", rand2.seed >= System.currentTimeMillis.toInt)

    val rand3 = Random.javaWithSeed(3)
    assertEquals("seed 3", rand3.seed, 3)
    assertEquals(rand3.nextNaturalUpto(6), 4)
    assertEquals(rand3.nextNaturalUpto(6), 0)

    val rand4 = Random.javaWithSeed(1)
    assertEquals("seed 4", rand4.seed, 1)
    assertEquals(rand4.nextNaturalUpto(6), 4)
    assertEquals(rand4.nextNaturalUpto(6), 2)

    // range, array, sphere, shuffle
  }

  @Test def testEntry: Unit = {
    val rand1 = Random.javaWithSeed(1)
    assertEquals(rand1.nextEntry(List("a", "b", "c", "d", "e", "f")), "e")
  }

  @Test def testRange: Unit = {
    val rand1 = Random.javaWithSeed(1)
    assertEquals(rand1.nextIntBetween(1)(7), 5)
    assertEquals(rand1.nextIntBetween(2)(8), 4)
    assertEquals(rand1.nextIntBetween(-1)(5), 0)
  }

  @Test def testShuffle: Unit = {
    val rand1 = Random.javaWithSeed(1)
    assertEquals(rand1.shuffle(List("a", "b", "c", "d", "e", "f")), List("f", "b", "d", "a", "c", "e"))
  }

  @Test def testSphere: Unit =  { 
    val rand1 = Random.javaWithSeed(1)
    val inUnit = rand1.inUnitSphere()
    assertTrue(inUnit.distance(Vector3.zero)<1)
    val onUnit = rand1.onUnitSphere()
    assertEquals(onUnit.distance(Vector3.zero), 1d, 0.00000001d)
  }

  @Test def testCircle: Unit =  { 
    val rand1 = Random.javaWithSeed(1)
    val inUnit = rand1.inUnitCircle()
    assertTrue(inUnit.distance(Vector2.zero)<1)
    val onUnit = rand1.onUnitCircle()
    assertEquals(onUnit.distance(Vector2.zero), 1d, 0.00000001d)
  }
}
