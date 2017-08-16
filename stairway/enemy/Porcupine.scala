
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import game.attack.{PlayerAttack, AttackNature, EnemyAttack, FlightLevel}
import util.tap._

class Porcupine(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  override def spoils: Spoils = Spoils.Money((39 + 3 * entropy.reward).toInt)

  override def startingHealth: Double = 50 + 3 * entropy.reward

  override def attackPower: Double = 8.0 + 1.0 * entropy.reward

  override def imageIndex: Int = 34

  override def takeDamage(attack: PlayerAttack): Unit = {
    master.player.takeDamage(EnemyAttack(attackPower, AttackNature(FlightLevel.Special)))
    super.takeDamage(attack)
  }

  override def name = "Porcupine"

  override def desc = "Deals damage to attacker when attacked"

}
