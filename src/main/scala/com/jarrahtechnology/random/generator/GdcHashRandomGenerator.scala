package com.jarrahtechnology.random

/** This fast hashing algorithm comes GDC video "Noise-Based RNG", available at https://www.youtube.com/watch?v=LWFzPP8ZbdU
  *
  * This is not thread-safe
  */
final class GdcHashRandomGenerator(val seed: Int) extends RandomGenerator {

  private val noise1 = 0xb5297a4d
  private val noise2 = 0x68e31da4
  private val noise3 = 0x1b56c4e9

  private var position = 0

  def next01(): Double = { // don't care about overflow in this function
    position += 1
    var result = seed + position
    result *= noise1
    result ^= (result >> 8)
    result += noise2
    result ^= (result << 8)
    result *= noise3
    result ^= (result >> 8)
    RandomGenerator.shift01(result)
  }

  def deepCopy(): GdcHashRandomGenerator = {
    val copy = new GdcHashRandomGenerator(seed)
    copy.position = position
    copy
  }
}
