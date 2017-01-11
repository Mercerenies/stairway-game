
package com.mercerenies.stairway.game.attack

import com.mercerenies.stairway.game.{StandardGame, Player}
import com.mercerenies.stairway.enemy.Enemy

abstract class PlayerAttack(val master: StandardGame.Master) {

  def player: Player = master.player

  def damage(enemy: Enemy): Double

  def attackUsed(enemy: Enemy): Unit = {}

  def perform(): Unit = master.currentEnemy match {
    case None => {}
    case Some(enemy) => {
      enemy.takeDamage(this)
      attackUsed(enemy)
    }
  }

}
