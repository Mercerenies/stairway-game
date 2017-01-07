
package com.mercerenies.stairway.util

object Numeral {

  def times(x: Int): String = x match {
    case 1 => "once"
    case 2 => "twice"
    case 3 => "thrice"
    case _ => x + " times"
  }

}
