
package com.mercerenies.stairway.enemy

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

  def instantKill(reward: Boolean): Unit = {
    if (reward && innerEnemy.isAlive)
      innerEnemy.instantKill()
    _alive = false
  }

}
