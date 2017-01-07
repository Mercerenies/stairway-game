
package com.mercerenies.stairway.action

import java.awt.event.MouseEvent.{BUTTON1, BUTTON2, BUTTON3}
import java.awt.event.MouseEvent

sealed trait MouseButton {
  def id: Int
}

object MouseButton {

  def button(x: MouseEvent) = x.getButton match {
    case BUTTON1 => Some(Left)
    case BUTTON2 => Some(Middle)
    case BUTTON3 => Some(Right)
    case _       => None
  }

  case object Left extends MouseButton {
    val id = BUTTON1
  }

  case object Middle extends MouseButton {
    val id = BUTTON2
  }

  case object Right extends MouseButton {
    val id = BUTTON3
  }

}
