
package com.mercerenies.stairway.util

object Degrees {

  def apply[T](x: T)(implicit num: Fractional[T], cast: Double => T): T = {
    import num._
    x * cast(math.Pi) / 180
  }

}
