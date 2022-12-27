package com.jarrahtechnology.random

import org.junit.Test
import junit.framework.TestCase
import org.junit.Assert.*

import scala.language.implicitConversions

class RewindableRandomTest extends TestCase {

  @Test def testRewind: Unit = {
    val rand1 = Random.rewindableJavaWithSeed(1)
    assertEquals(rand1.seed, 1)
    assertEquals(rand1.nextNaturalUpto(6), 4)
    assertEquals(rand1.nextNaturalUpto(6), 2)
    rand1.rewind(1)
    assertEquals(rand1.nextNaturalUpto(6), 2)
    rand1.rewind(2)
    assertEquals(rand1.nextNaturalUpto(6), 4)
    assertEquals(rand1.nextNaturalUpto(6), 2)
  }

  @Test def testRewindTooMuch: Unit = {
    val rand1 = Random.rewindableJavaWithSeed(1)
    assertEquals(rand1.seed, 1)
    assertEquals(rand1.nextNaturalUpto(6), 4)
    assertEquals(rand1.nextNaturalUpto(6), 2)
    rand1.rewind(4)
    assertEquals(rand1.nextNaturalUpto(6), 4)
    assertEquals(rand1.nextNaturalUpto(6), 2)
  }

  @Test def testRewindEmpty: Unit = {
    val rand1 = Random.rewindableJavaWithSeed(1)
    rand1.rewind(4)
    assertEquals(rand1.nextNaturalUpto(6), 4)
    assertEquals(rand1.nextNaturalUpto(6), 2)
  }

  @Test def testRewindCommit: Unit = {
    val rand1 = Random.rewindableJavaWithSeed(1)
    assertEquals(rand1.seed, 1)
    assertEquals(rand1.nextNaturalUpto(6), 4)
    assertEquals(rand1.nextNaturalUpto(6), 2)
    rand1.rewind(1)
    rand1.reset()

    assertEquals(rand1.nextNaturalUpto(6), 1)
  }

  @Test def testPeek: Unit = {
    val rand1 = Random.rewindableJavaWithSeed(1)
    assertEquals(rand1.peekNext(), 0.7308781907032909, 0.00000001d)
    assertEquals(rand1.peekNext(), 0.7308781907032909, 0.00000001d)
    assertEquals(rand1.peekNext(), 0.7308781907032909, 0.00000001d)
    assertEquals(rand1.nextNaturalUpto(6), 4)
    assertEquals(rand1.peekNext(), 0.41008081149220166, 0.00000001d)
  }

  @Test def testCalls: Unit = {
    val rand1 = Random.rewindableJavaWithSeed(1)
    assertEquals(0, rand1.maxRewind)
    assertEquals(0, rand1.maxRewind)
    rand1.nextNaturalUpto(6)
    rand1.nextNaturalUpto(6)
    assertEquals(2, rand1.maxRewind)
    rand1.rewind(2)
    assertEquals(0, rand1.maxRewind)
    rand1.peekNext()
    assertEquals(0, rand1.maxRewind)
    rand1.nextNaturalUpto(6)
    rand1.nextNaturalUpto(6)
    assertEquals(2, rand1.maxRewind)
    rand1.reset()
    assertEquals(0, rand1.maxRewind)
    rand1.nextNaturalUpto(6)
    assertEquals(1, rand1.maxRewind)
  }

}
