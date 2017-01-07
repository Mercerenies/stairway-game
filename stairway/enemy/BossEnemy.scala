
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.{Player, StandardGame}

trait BossEnemy extends Enemy {

  def bossName: String

  override def isBoss: Boolean = true

  override def onDeath(player: Player): Unit = {
    super.onDeath(player)
    player.master.advanceEra()
  }

}
