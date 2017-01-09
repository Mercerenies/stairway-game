
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.{Player, StandardGame}
import com.mercerenies.stairway.game.attack.PlayerAttack
import com.mercerenies.stairway.util.{Rectangle, GraphicsImplicits}
import java.awt.Graphics2D

class EnemyTeam(override val master: StandardGame.Master, val fullTeam: Enemy*)
    extends Enemy {
  import GraphicsImplicits._

  val positions = EnemyTeam.positions(fullTeam.size)

  def team: Seq[Enemy] = fullTeam.filter(_.isAlive)

  override def attack(player: Player) = {
    team.foreach(_.attack(player))
  }

  override def isBoss: Boolean = fullTeam.exists(_.isBoss)

  override def isAlive: Boolean = fullTeam.exists(_.isAlive)

  override def spoils: Spoils = Spoils.None // Handled internally

  override def takeDamage(attack: PlayerAttack) = {
    team.foreach(_.takeDamage(attack))
    super.takeDamage(attack)
  }

  override def step(): Unit = {
    fullTeam.foreach(_.step())
  }

  override def resolveStatuses(): Unit = {
    // Occasionally, an individual team member will have a status that
    // the whole team lacks as a whole.
    team.foreach(_.resolveStatuses())
    super.resolveStatuses()
  }

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    fullTeam zip positions foreach { case (e, (x, y)) =>
      if (e.isAlive)
        e.draw(graph, rect.shift(x, y))
    }
  }

}

object EnemyTeam {

  val positions = Array(
    List(),
    List((0, 0)),
    List((0, 0), (-48, -16)),
    List((0, 0), (-48, -16), (48, -16)),
    List((0, 0), (-48, -16), (48, -16), (48, 16)),
    List((0, 0), (-48, -16), (48, -16), (48, 16), (48, -16))
  )

}
