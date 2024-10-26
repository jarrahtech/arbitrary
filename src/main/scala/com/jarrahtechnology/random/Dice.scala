package com.jarrahtechnology.random

trait Dice {
  def roll(using rand: Random): Int

  def negate: Dice = MinusDie(this)
  def unary_- : Dice = negate
  def add(n: Int): Dice = DiePlusDie(this, Constant(n))
  def +(n: Int): Dice = add(n)
  def subtract(n: Int): Dice = DiePlusDie(this, Constant(-n))
  def -(n: Int): Dice = subtract(n)
  def add(d: Dice): Dice = DiePlusDie(this, d)
  def +(d: Dice): Dice = add(d)
  def subtract(d: Dice): Dice = DiePlusDie(this, MinusDie(d))
  def -(d: Dice): Dice = subtract(d)
  def multiply(n: Int): Dice = DieMultDie(this, Constant(n))
  def *(n: Int): Dice = multiply(n)
  def divide(n: Int): Dice = DieDivideDie(this, Constant(n))
  def /(n: Int): Dice = divide(n)

  def repeat(n: Int)(using rand: Random): Dice = RepeatDice(this, n)
  def reroll(f: Int => Boolean)(using rand: Random) = map { x => if (f(x)) roll else x }
  def map(f: Int => Int)(using rand: Random) = MapDice(this, f)
}

object Dice {
  def roll(d: Dice)(using rand: Random): Int = d.roll
  def d(sides: Int) = Die(sides)

  def d4 = d(4)
  def d6 = d(6)
  def d8 = d(8)
  def d10 = d(10)
  def d12 = d(12)
  def d20 = d(20)
  def d100 = d(100)
}

case class Constant(n: Int) extends Dice {
  def roll(using rand: Random) = n
  override def add(nn: Int) = Constant(n + nn)
  override def subtract(nn: Int) = Constant(n - nn)

  def d(sides: Int) = RepeatDice(Die(sides), n)
  def apply(d: Die) = RepeatDice(d, n)
}

given Conversion[Int, Constant] = Constant(_)

case class Die(sides: Int) extends Dice {
  def roll(using rand: Random) = rand.nextNaturalUpto(sides) + 1
}

case class RepeatDice(d: Dice, repeats: Int) extends Dice {
  def roll(using rand: Random) = { var result = 0; for (_ <- 1 to repeats) { result += d.roll; }; result; }
}

case class MapDice(d: Dice, f: Int => Int) extends Dice {
  def roll(using rand: Random) = f(d.roll)
}

case class MinusDie(d: Dice) extends Dice {
  def roll(using rand: Random) = -d.roll
}

case class DiePlusDie(d1: Dice, d2: Dice) extends Dice {
  def roll(using rand: Random) = d1.roll + d2.roll
}

case class DieMultDie(d1: Dice, d2: Dice) extends Dice {
  def roll(using rand: Random) = d1.roll * d2.roll
}

case class DieDivideDie(d1: Dice, d2: Dice) extends Dice {
  def roll(using rand: Random) = d1.roll / d2.roll
}
