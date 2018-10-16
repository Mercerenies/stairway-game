
package com.mercerenies.stairway.action

import java.awt.event.MouseEvent.{BUTTON1, BUTTON2, BUTTON3}
import java.awt.event.MouseEvent

/** A button on the mouse.
  */
sealed trait MouseButton {
  /** The ID, according to java.awt.event.MouseEvent.
    */
  def id: Int
}

object MouseButton {

  /** Accesses the button of a java.awt.event.MouseEvent instance as a
    * [[MouseButton]] instance, returning None if the MouseEvent is
    * not valid or does not represent a button press.
    *
    * @param x the event
    * @return the button, or None if the event was not based on a button
    */
  def button(x: MouseEvent) = x.getButton match {
    case BUTTON1 => Some(Left)
    case BUTTON2 => Some(Middle)
    case BUTTON3 => Some(Right)
    case _       => None
  }

  /** The left mouse button.
    */
  case object Left extends MouseButton {
    val id = BUTTON1
  }

  /** The middle mouse button.
    */
  case object Middle extends MouseButton {
    val id = BUTTON2
  }

  /** The right mouse button.
    */
  case object Right extends MouseButton {
    val id = BUTTON3
  }

}
