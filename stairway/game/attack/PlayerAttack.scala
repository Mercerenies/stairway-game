
package com.mercerenies.stairway
package game.attack

import game.{StandardGame, Player}
import enemy.Enemy

abstract class PlayerAttack(val master: StandardGame.Master) {

  def player: Player = master.player

  def nature: AttackNature = AttackNature(FlightLevel(player.isFlying))

  def damage(enemy: Enemy): Double

  def attackUsed(enemy: Enemy): Unit = {}

  def perform(): Unit = master.currentEnemy match {
    case None => {}
    case Some(enemy) =>
      enemy.takeDamage(this)
      attackUsed(enemy)
  }

}
