
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.Player

class EnemyBox[+A <: Enemy](val innerEnemy: A) {

  private var _alive = innerEnemy.isAlive

  def isAlive: Boolean = _alive
  def hasBeenKilled: Boolean = !isAlive

  def enemy: Option[A] = isAlive match {
    case true => Some(innerEnemy)
    case false => None
  }

  def refresh(): Unit = {
    if (!innerEnemy.isAlive)
      _alive = false
  }

  def instantKill(player: Player, reward: Boolean): Unit = {
    if (reward && innerEnemy.isAlive)
      innerEnemy.instantKill(player)
    _alive = false
  }

}
