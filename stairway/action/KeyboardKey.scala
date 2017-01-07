
package com.mercerenies.stairway.action

import java.awt.event.KeyEvent._

case class KeyboardKey(code: Int)

object KeyboardKey {

  def alphanum(x: Char): Option[KeyboardKey] =
    if (x.isLetterOrDigit) Some(KeyboardKey(x.toInt)) else None

}
