
package com.mercerenies.stairway.action

import com.mercerenies.stairway.game.GameLoop

abstract trait Action {

  def react(gameLoop: GameLoop)

}
