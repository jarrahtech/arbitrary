package com.jarrahtechnology.random

trait RandomGenerator {
  def seed: Int
  def next01(): Double
  def deepCopy(): RandomGenerator
}

object RandomGenerator {
  def clampNatural(result: Int) = if (result == Int.MaxValue) then result - 1 else if (result < 0) then result - Int.MinValue else result
  def shift01(result: Int) = clampNatural(result) / Int.MaxValue.toDouble
}
