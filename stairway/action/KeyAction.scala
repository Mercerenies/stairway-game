
package com.mercerenies.stairway.action

import com.mercerenies.stairway.game.GameLoop

case class KeyAction(key: KeyboardKey, eventType: ActionType) extends Action {

  def react(gameLoop: GameLoop) = {
    import MouseButton._
    val writer = gameLoop.state.Writer
    eventType match {
      case ActionType.Down =>
        writer.keyPressed(key)
      case ActionType.Up =>
        writer.keyReleased(key)
    }
  }

}
