package com.jarrahtechnology.random

import org.junit.Test
import junit.framework.TestCase
import org.junit.Assert.*

import scala.language.implicitConversions
import scala.language.postfixOps

import com.jarrahtechnology.random.Dice.*

class DiceTest extends TestCase {

  @Test def testRoll: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(3, d4 roll)
    assertEquals(9, d20 roll)
  }

  @Test def testObjectRoll: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(5, Dice.roll(d6))
  }

  @Test def testRoll2: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(5, d6 roll)
    assertEquals(3, d6 roll)
  }

  @Test def testAdd: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(5, d4 + 2 roll)
  }

  @Test def testMinus: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(1, d4 - 2 roll)
  }

  @Test def testAddAdd: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(6, d4 + 2 + 1 roll)
  }

  @Test def testAddDice: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(6, d4 + d6 roll)
  }

  @Test def testAddAddDice: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(8, d4 + 2 + d6 roll)
  }

  @Test def testMinusDice: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(-6, d4 - d20 roll)
  }

  @Test def testRepeatDice: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(8, d6.repeat(2) roll)
  }

  @Test def testRerollDice: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(5, d6 reroll { _ == 4 } roll) // no reroll
    assertEquals(2, d6 reroll { _ == 3 } roll) // reroll
  }

  @Test def testMapDice: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(2, d6 map { x => if (x == 5) 2 else x } roll)
  }

  @Test def testMultDice: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(15, d6 * 3 roll)
    assertEquals(1, d6 / 2 roll)
  }

  @Test def testConstant: Unit = {
    val c2 = Constant(2)
    assertEquals(Constant(15), c2 + 13)
    assertEquals(Constant(1), c2 -1)

    implicit val rand = Random.javaWithSeed(1)
    assertEquals(8, c2(d6) roll)
  }

  @Test def testConstantRoll: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    val c2 = Constant(2)
    assertEquals(15, c2 + 13 roll)
    assertEquals(1, c2 -1 roll)
  }

  @Test def testConstantRepeat: Unit = {
    val c2 = Constant(2)
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(8, c2.d(6) roll)
  }

  @Test def testNegate: Unit = {
    implicit val rand = Random.javaWithSeed(1)
    assertEquals(-3, d4.negate.roll)
    assertEquals(-9, -d20 roll)
  }
}
