
package com.mercerenies.stairway
package enemy

import space.{Space, EnemySpace, FruitTheftSpace}
import game.{Player, StandardGame}

class Burglar(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  override def spoils: Spoils = Spoils.Money((42 + 1 * entropy.reward).toInt)

  override def startingHealth: Double = 40.0 + 4.0 * entropy.risk

  override def attackPower: Double = 7.0 + 1.0 * entropy.risk

  override def attack(player: Player): Unit = {
    super.attack(player)
    val master = player.master
    val targets = List(
      master.buttonPad.orangeButton,
      master.buttonPad.melonButton,
      master.buttonPad.appleButton
    ) filter (_.count > 0)
    if (!targets.isEmpty) {
      val target = targets.maxBy(_.count)
      target.count -= 1
      master.particleText.addParticle(
        s"-1 ${target.product}",
        FruitTheftSpace.color,
        player.drawRect,
        (-90.0, 45.0)
      )
    }
  }

  override def imageIndex: Int = 36

}
