
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import game.attack.PlayerAttack

class UnspilledMilk(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  private var _shattered = false

  override def spoils: Spoils = if (_shattered) Spoils.None else Spoils.Money(22)

  override def startingHealth: Double = 7.0

  override def attackPower: Double = 4.0

  override def imageIndex: Int = 31

  override def takeDamage(attack: PlayerAttack): Unit = {
    super.takeDamage(attack)
    if (isAlive) {
      master.player nextNPositions 3 foreach { pos =>
        master.belt.feed.assignOverride(pos, space.SpilledMilkSpace)
      }
      _shattered = true // Don't award the spoils for this kill
      this.defeatText = "Shatter!"
      this.instantKill(master.player)
    }
  }

}
