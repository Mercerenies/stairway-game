
package com.mercerenies.stairway.action

import com.mercerenies.stairway.game.GameLoop

/** An action, such as a mouse click or key press, that the game can
  * react to.
  */
abstract trait Action {

  /**
    * Manipulates the game loop's behavior and state, according to the
    * action's parameters and nature.
    *
    * @param gameLoop the game loop object
    */
  def react(gameLoop: GameLoop)

}
