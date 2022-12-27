package com.jarrahtechnology.random

import java.util.concurrent.atomic.AtomicLong

/** This algorithm is a copy of the Random in the Java API, but made copyable
  *
  * This is thread-safe
  */
final class JavaRandomGenerator(val seed: Int) extends RandomGenerator {

  private val multiplier: Long = 0x5deece66dL
  private val addend: Long = 0xbL
  private val mask: Long = (1L << 48) - 1
  private val doubleUnit: Double = 1.0 / (1L << 53)

  private lazy val atomicSeed: AtomicLong = new AtomicLong((seed.toLong ^ multiplier) & mask)

  private def next(bits: Int): Int = {
    var oldseed: Long = 0
    var nextseed: Long = 0
    while {
      oldseed = atomicSeed.get()
      nextseed = (oldseed * multiplier + addend) & mask
      !atomicSeed.compareAndSet(oldseed, nextseed)
    } do ()
    (nextseed >>> (48 - bits)).toInt
  }

  def next01(): Double = {
    val high = next(26).toLong << 27
    val low = next(27).toLong
    val result = (high + low) * doubleUnit
    result
  }

  def deepCopy() = {
    val copy = new JavaRandomGenerator(seed)
    copy.atomicSeed.set(atomicSeed.get())
    copy
  }
}
