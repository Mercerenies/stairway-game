
package com.mercerenies.stairway
package enemy

import space.{Space, EnemySpace, SpilledMilkSpace}
import game.{Player, StandardGame}

class GrumpyCone(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master)
    with TurnCounterEnemy {

  override def spoils: Spoils = Spoils.Money((36 + 6 * entropy.reward).toInt)

  override def startingHealth: Double = 18.0 + 3.0 * entropy.risk

  override def attackPower: Double = 4.0 + 1.0 * entropy.risk

  private def isOwnEnemySpace(space: Space) = space match {
    case EnemySpace(box) if box.innerEnemy == this => true
    case _ => false
  }

  override def imageIndex: Int = 5

  override def attack(player: Player): Unit = {
    if (isEndOfCycle(GrumpyCone.attackCycle)) {
      // Use the ice cream attack
      val belt = master.belt
      val matching = player.nextPositions takeWhile { idx => isOwnEnemySpace(belt.getSpace(idx)) }
      matching.lastOption match {
        case None =>
          super.attack(player) // Can't do it; use normal attack
        case Some(idx) =>
          belt.feed.assignOverride(idx, SpilledMilkSpace)
          master.particleText.addParticle("Drip!", Enemy.particleColor, rect)
      }
    } else {
      // Perform the standard attack
      super.attack(player)
    }
    advanceCounter()
  }

}

object GrumpyCone {
  val attackCycle = 3
}
