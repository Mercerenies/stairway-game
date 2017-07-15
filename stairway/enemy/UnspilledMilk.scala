
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import game.attack.PlayerAttack

class UnspilledMilk(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  override def spoils: Spoils = Spoils.Money(22)

  override def startingHealth: Double = 7.0

  override def attackPower: Double = 4.0

  override def imageIndex: Int = 31

  override def takeDamage(attack: PlayerAttack): Unit = {
    super.takeDamage(attack)
    if (isAlive) {
      master.player nextNPositions 3 foreach { pos =>
        master.belt.feed.assignOverride(pos, space.SpilledMilkSpace)
      }
      master.currentEnemyBox.foreach(_.instantKill(master.player, false))
      master.particleText.addParticle("Shatter!", Enemy.particleColor, rect)
    }
  }

}
