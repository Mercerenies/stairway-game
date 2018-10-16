
package com.mercerenies.stairway.action

import com.mercerenies.stairway.game.GameLoop

/** A mouse click.
  *
  * A mouse click consists of a button, a direction (up or down), and
  * a position on the screen.
  *
  * @constructor
  * @param button the mouse button
  * @param eventType the direction
  * @param position the position on the screen
  */
case class MouseClick(button: MouseButton, eventType: ActionType, position: (Int, Int)) extends Action {

  /** Mouse clicks are stored in the game's internal state when
    * resolved.
    */
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
