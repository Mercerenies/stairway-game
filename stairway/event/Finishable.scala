
package com.mercerenies.stairway.event

import com.mercerenies.stairway.game.{GameEntity, GameMaster}

trait Finishable[T] extends Event[T] {

  def owner: GameEntity[GameMaster]
  def finish(): Unit

}
