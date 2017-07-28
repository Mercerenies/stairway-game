
package com.mercerenies.stairway
package util

object tap {

  implicit class Tap[A](val value: A) extends AnyVal {

    def tap[B](arg: A => B): A = {
      arg(value)
      value
    }

  }

}
