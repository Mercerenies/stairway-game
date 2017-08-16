
package com.mercerenies.stairway
package enemy

import space.{Space, EnemySpace, SpilledMilkSpace}
import game.{Player, StandardGame}
import util.tap._

class Tree(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  override def spoils: Spoils = Spoils.Money((44 + 3 * entropy.reward).toInt)

  override def startingHealth: Double = 70.0 + 10.0 * entropy.risk

  override def attackPower: Double = 5.0

  override def imageIndex: Int = 7

  override def attack(player: Player): Option[Double] = {
    super.attack(player).tap { atk =>
      val field = player.master.field
      atk.foreach { dmg =>
        player.master.field.rootEnergy += dmg * Tree.damageMult
      }
      val heal = List(
        field.rootEnergy,
        healthBar.max.toDouble - healthBar.value.toDouble,
        Tree.maxHeal
      ).min
      field.rootEnergy -= heal
      healthBar.value += heal
    }
  }

  override def name = "Tree"

  override def desc = "Heals from roots each turn; every successful attack fuels the tree's roots"

}

object Tree {
  val damageMult = 0.5
  val maxHeal = 0.5
}
