
package com.mercerenies.stairway.action

import com.mercerenies.stairway.game.GameLoop

/** A type of action involving a key being pressed or released.
  *
  * @constructor
  * @param key the key
  * @param eventType whether the action was down or up
  */
case class KeyAction(key: KeyboardKey, eventType: ActionType) extends Action {

  /** A key press is applied to the game's internal state as soon as it
    * is resolved.
    */
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
