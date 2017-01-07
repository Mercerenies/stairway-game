
package com.mercerenies.stairway

package object util {

  val rand: scala.util.Random = scala.util.Random

  def clamp[T](x: T, lower: T, upper: T)(implicit ord: Ordering[T]): T = {
    ord.max(ord.min(x, upper), lower)
  }

  def toward(src: Double, dest: Double, spd: Double): Double = {
    if (math.abs(dest - src) <= spd)
      dest
    else
      src + math.signum(dest - src) * math.abs(spd)
  }

  def lerp(lower: Double, upper: Double, x: Double): Double = {
    lower * (1 - x) + upper * x
  }

  def toVector(magnitude: Double, direction: Double): (Double, Double) =
    (magnitude * math.cos(Degrees(direction)), magnitude * math.sin(Degrees(direction)))

  def mod[T : Integral](x: T, y: T): T = {
    import Integral.Implicits._
    (x % y + y) % y
  }

  def cycle[T](xs: T*): Stream[T] =
    Stream.continually(List(xs: _*)).flatten

}
