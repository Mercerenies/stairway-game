
package com.mercerenies.stairway.action

import java.awt.event.KeyEvent._

/** A keyboard key is simple an integer key code, usually the OS
  * virtual key code.
  *
  * @constructor
  * @param code the key code
  */
case class KeyboardKey(code: Int)

object KeyboardKey {

  /** Constructs a [[KeyboardKey]] instance if the character corresponds
    * to an alphanumeric character. If the character is not
    * alphanumeric, then None is returned.
    *
    * @param x the character
    * @return the key, or None if not alphanumeric
    */
  def alphanum(x: Char): Option[KeyboardKey] =
    if (x.isLetterOrDigit) Some(KeyboardKey(x.toInt)) else None

}
