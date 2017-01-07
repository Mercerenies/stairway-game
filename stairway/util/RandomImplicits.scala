
package com.mercerenies.stairway.util

import scala.util.Random

object RandomImplicits {

  implicit class RichRandom(val random: Random) extends AnyVal {

    def nextInt(min: Int, max: Int): Int = {
      random.nextInt(max - min) + min
    }

    def nextDouble(min: Double, max: Double): Double = {
      random.nextDouble() * (max - min) + min
    }

    def nextOf[T](seq: Seq[T]): T = {
      seq(random nextInt seq.length)
    }

  }

}
