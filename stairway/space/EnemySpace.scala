
package com.mercerenies.stairway
package space

import game.attack
import game.StandardGame
import game.content.EnemyContent
import enemy.{EnemyBox, Enemy}

case class EnemySpace(enemy: EnemyBox[Enemy]) extends ImageSpace {

  override def imageIndex: Int = if (enemy.innerEnemy.isBoss) 8 else 3

  override def onLand(master: StandardGame.Master) = {
    val player = master.player
    val contentArea = master.contentArea
    contentArea.content match {
      case Some(enemyContent: EnemyContent) if enemyContent.enemy == enemy => {
        // The same enemy is already in play
        enemyContent.enemy.enemy.foreach { enemy =>
          enemy.attack(player)
          enemy.resolveStatuses()
        }
      }
      case _ => {
        // The enemy is not in play
        contentArea.put(new EnemyContent(contentArea, enemy))
      }
    }
  }

  override def onDepart(master: StandardGame.Master) = {
    val player = master.player
    val contentArea = master.contentArea
    (player.nextSpace, contentArea.content) match {
      case (space: EnemySpace, Some(enemyContent: EnemyContent)) if enemyContent.enemy == space.enemy => {
        // The next space is for the same enemy
      }
      case _ => {
        // The next space is not the same enemy
        contentArea.clear()
      }
    }
  }

  override def onEmulate(master: StandardGame.Master) = {
    val apples = master.buttonPad.appleButton
    while (enemy.isAlive) {
      while ((master.meter.health.value.toDouble <= 50.0) && (apples.count > 0)) {
        apples.product.use(master.player)
        apples.count -= 1
      }
      enemy.innerEnemy.takeDamage(new attack.MagnifiedAttack(new attack.PhysicalAttack(master), 1.5))
      enemy.refresh()
      enemy.innerEnemy.attack(master.player)
      enemy.refresh()
    }
  }

  override def name = if (enemy.innerEnemy.isBoss) "Boss Space" else "Enemy Space"

  override def desc = "Fight an enemy while you're on this sequence of spaces"

}
