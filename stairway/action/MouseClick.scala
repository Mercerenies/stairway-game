
package com.mercerenies.stairway.action

import com.mercerenies.stairway.game.GameLoop

case class MouseClick(button: MouseButton, eventType: ActionType, position: (Int, Int)) extends Action {

  def react(gameLoop: GameLoop) = {
    import MouseButton._
    for (elem <- gameLoop.master.objects) {
      elem.click(this)
    }
    val writer = gameLoop.state.Writer
    this.button match {
      case Left => { writer.leftMouse = eventType.toBoolean }
      case Middle => { writer.middleMouse = eventType.toBoolean }
      case Right => { writer.rightMouse = eventType.toBoolean }
    }
  }

}
