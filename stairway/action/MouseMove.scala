
package com.mercerenies.stairway.action

import com.mercerenies.stairway.game.GameLoop

/** A mouse move action simply involves the mouse being moved from one
  * position to another. The new position is stored.
  *
  * @constructor
  * @param position the position on the screen
  */
case class MouseMove(position: (Int, Int)) extends Action {

  /** Mouse movements update the mouse's position according to the
    * game's internal state.
    */
  def react(gameLoop: GameLoop) = {
    val writer = gameLoop.state.Writer
    writer.mousePos = position
  }

}
