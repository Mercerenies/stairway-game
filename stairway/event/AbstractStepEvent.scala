
package com.mercerenies.stairway.event

import com.mercerenies.stairway.game.{GameMaster, GameEntity}

abstract class AbstractStepEvent(val owner: GameEntity[GameMaster]) extends StepEvent with Finishable[Unit] {

  override def finish() = owner.stepEvent -= this

}
