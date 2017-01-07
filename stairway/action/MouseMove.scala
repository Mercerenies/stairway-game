
package com.mercerenies.stairway.action

import com.mercerenies.stairway.game.GameLoop

case class MouseMove(position: (Int, Int)) extends Action {

  def react(gameLoop: GameLoop) = {
    val writer = gameLoop.state.Writer
    writer.mousePos = position
  }

}
