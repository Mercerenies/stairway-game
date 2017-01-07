
package com.mercerenies.stairway.event

import com.mercerenies.stairway.action.{MouseClick, ActionType, MouseButton}

abstract class AbstractClickEvent extends ClickEvent {

  override def call(click: MouseClick): Unit = {
    click match {
      case MouseClick(MouseButton.Left  , ActionType.Down, (x, y)) =>   leftMouseDown(x, y)
      case MouseClick(MouseButton.Left  , ActionType.Up  , (x, y)) =>     leftMouseUp(x, y)
      case MouseClick(MouseButton.Middle, ActionType.Down, (x, y)) => middleMouseDown(x, y)
      case MouseClick(MouseButton.Middle, ActionType.Up  , (x, y)) =>   middleMouseUp(x, y)
      case MouseClick(MouseButton.Right , ActionType.Down, (x, y)) =>  rightMouseDown(x, y)
      case MouseClick(MouseButton.Right , ActionType.Up  , (x, y)) =>    rightMouseUp(x, y)
    }
  }

  def leftMouseDown(x: Int, y: Int) = {}
  def leftMouseUp(x: Int, y: Int) = {}
  def middleMouseDown(x: Int, y: Int) = {}
  def middleMouseUp(x: Int, y: Int) = {}
  def rightMouseDown(x: Int, y: Int) = {}
  def rightMouseUp(x: Int, y: Int) = {}

}
