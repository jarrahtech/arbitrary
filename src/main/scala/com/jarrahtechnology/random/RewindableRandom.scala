package com.jarrahtechnology.random

import scala.collection.mutable.Stack

/** This is not thread-safe
  */
case class RewindableRandom(source: Random) extends Random {

  private val reuse: Stack[Double] = Stack[Double]()
  private val rands: Stack[Double] = Stack[Double]()

  def seed = source.seed

  def nextDouble01() = {
    val result = if reuse.isEmpty then source.nextDouble01() else reuse.pop
    rands.push(result)
    result
  }

  def maxRewind = rands.length

  def deepCopy() = {
    val copy = new RewindableRandom(source.deepCopy());
    copy.reuse.pushAll(reuse)
    copy.rands.pushAll(rands)
    copy
  }

  def peekNext() = {
    val result = nextDouble01()
    rewind(1)
    result
  }

  def reset() = {
    reuse.clear()
    rands.clear()
  }

  def rewind(num: Int) = {
    for (i <- 1 to math.min(num, maxRewind)) {
      reuse.push(rands.pop())
    }
  }
}
