package com.jarrahtechnology.random

import scala.collection.{SeqOps, BuildFrom}
import com.jarrahtechnology.util.{Vector2, Vector3}

trait Random {
  def seed: Int
  def nextDouble01(): Double
  def deepCopy(): Random

  def nextNatural(): Int = (nextDouble01() * Int.MaxValue).toInt
  def nextNaturalUpto = nextIntBetween(0)
  def nextBoolean(): Boolean = nextNatural() % 2 == 0

  def nextIntBetween(minInclusive: Int)(maxExclusive: Int): Int = {
    require(minInclusive < maxExclusive, "min>=max")
    val range = maxExclusive - minInclusive
    require(range >= 0, "range too large") // if <0 then overflow and the real range is greater than Int.MaxValue
    math.floor(nextDouble01() * range).toInt + minInclusive
  }

  def nextDoubleBetween(minInclusive: Double)(maxExclusive: Double): Double = {
    require(minInclusive < maxExclusive, "min>=max")
    nextDouble01() * (maxExclusive - minInclusive) + minInclusive
  }
  def nextPositiveDoubleUpto = nextDoubleBetween(0)

  def nextEntry[T](seq: Seq[T]): T = seq.apply(nextNaturalUpto(seq.length))
  def shuffle[T, C](xs: IterableOnce[T])(implicit bf: BuildFrom[xs.type, T, C]): C = { // Copied from scala.util.Random
    val buf = new scala.collection.mutable.ArrayBuffer[T] ++= xs

    def swap(i1: Int, i2: Int): Unit = {
      val tmp = buf(i1)
      buf(i1) = buf(i2)
      buf(i2) = tmp
    }

    for (n <- buf.length to 2 by -1) {
      val k = nextNaturalUpto(n)
      swap(n - 1, k)
    }

    (bf.newBuilder(xs) ++= buf).result()
  }

  def onCircle(radius: Double): Vector2 = {
    val angle = nextDouble01() * 2 * math.Pi
    Vector2(math.cos(angle) * radius, math.sin(angle) * radius)
  }
  def inUnitCircle(): Vector2 = onCircle(nextDouble01())
  def onUnitCircle(): Vector2 = onCircle(1d)

  def onSphere(radius: Double): Vector3 = {
        val u = nextDouble01()
        val v = nextDouble01()
        val theta = u * 2.0 * math.Pi
        val phi = math.acos(2.0 * v - 1.0)
        val sinPhi = math.sin(phi)
        val x = radius * sinPhi * math.cos(theta)
        val y = radius * sinPhi * math.sin(theta)
        val z = radius * math.cos(phi)
        Vector3(x, y, z)
    }
    def inUnitSphere(): Vector3 = onSphere(math.pow(nextDouble01(), 1/3f))
    def onUnitSphere(): Vector3 = onSphere(1d)
}

case class RandomGeneratorAdapter(source: RandomGenerator) extends Random {
  def seed: Int = source.seed
  def nextDouble01(): Double = source.next01()
  def deepCopy(): RandomGeneratorAdapter = new RandomGeneratorAdapter(source.deepCopy())
}

object Random {
  def javaWithSeed(seed: Int) = RandomGeneratorAdapter(JavaRandomGenerator(seed))
  def javaDefaultSeed() = RandomGeneratorAdapter(JavaRandomGenerator(0))
  def javaRandomSeed() = RandomGeneratorAdapter(JavaRandomGenerator(System.currentTimeMillis().toInt))

  def gdcWithSeed(seed: Int) = RandomGeneratorAdapter(GdcHashRandomGenerator(seed))
  def gdcDefaultSeed() = RandomGeneratorAdapter(GdcHashRandomGenerator(0))
  def gdcRandomSeed() = RandomGeneratorAdapter(GdcHashRandomGenerator(System.currentTimeMillis().toInt))

  def rewindableJavaWithSeed(seed: Int) = RewindableRandom(RandomGeneratorAdapter(JavaRandomGenerator(seed)))
  def rewindableJavaRandomSeed = RewindableRandom(RandomGeneratorAdapter(JavaRandomGenerator(System.currentTimeMillis().toInt)))
}