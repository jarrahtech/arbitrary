package com.jarrahtechnology.random

// This algorithm comes from Numerical Recipes in C (2nd Ed.). Similar, but not exactly the same as the one used in C#, but is the same as the one used in the JarrahTech C# Unity Tools library
// Note this does return exactly the same number due to numerical differences in different languages, but should be good to 15 decimal places
// Not thread-safe
@SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
final class NumericalRecipesRandomGenerator(val seed: Int, init: Boolean = true) extends RandomGenerator {
  private val seedAdjust = 161803398
  private val seedLength: Int = 56
  private val addressableIndexes: Int = seedLength - 1

  private var inext: Int = 0
  private var inextp: Int = 21
  private var seedArray: Array[Int] = new Array[Int](seedLength)

  if (init) { initRandom }

  def initRandom = {
    var mj: Int = seedAdjust - (if (seed == Int.MinValue) then Int.MaxValue else math.abs(seed))
    var mk: Int = 1
    // The first and last elements are special, so we ignore them in the fill loop
    seedArray(addressableIndexes) = mj
    for (i <- 1 until addressableIndexes) {
      val ii: Int = (21 * i) % addressableIndexes
      seedArray(ii) = mk
      mk = RandomGenerator.clampNatural(mj - mk)
      mj = seedArray(ii);
    }   
    for (k <- 1 to 4) {
      for (i <- 1 until seedLength) {
        seedArray(i) = RandomGenerator.clampNatural(seedArray(i) - seedArray(1 + (i + 30) % addressableIndexes))
      }
    }
  }

  def next01(): Double = {
    inext = (inext + 1) % seedLength
    inextp = (inextp + 1) % seedLength
    val result = RandomGenerator.clampNatural(seedArray(inext) - seedArray(inextp))
    seedArray(inextp) = result
    result * (1d/Int.MaxValue)
  }

  def deepCopy() = {
    val copy = new NumericalRecipesRandomGenerator(seed, false)
    copy.inext = inext
    copy.inextp = inextp
    copy.seedArray = seedArray.clone()
    copy
  }
}