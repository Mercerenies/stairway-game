
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import game.attack.{PlayerAttack, FragmentedAttack}
import game.tagline.Tagged
import util.{Rectangle, GraphicsImplicits}
import status.{StatusEffect, EffectPolicy}
import java.awt.Graphics2D

class EnemyTeam(override val master: StandardGame.Master, val fullTeam: Enemy*)
    extends Enemy {
  import GraphicsImplicits._

  val positions = EnemyTeam.positions(fullTeam.size)

  def team: Seq[Enemy] = fullTeam.filter(_.isAlive)

  override def attack(player: Player): Option[Double] = {
    team.map { _.attack(player) }.fold(None) { (a, b) =>
      for { a1 <- a ; b1 <- b } yield { a1 + b1 }
    }
  }

  override def isBoss: Boolean = fullTeam.exists(_.isBoss)

  override def isAlive: Boolean = fullTeam.exists(_.isAlive)

  override def spoils: Spoils = Spoils.None // Handled internally

  override def takeDamage(attack: PlayerAttack) = {
    val newAttack = new FragmentedAttack(attack, team.size)
    team.foreach(_.takeDamage(newAttack))
    super.takeDamage(attack)
  }

  override def step(): Unit = {
    fullTeam.foreach(_.step())
  }

  override def allStatuses: List[StatusEffect] =
    statuses ++ (for { enemy <- team ; status <- enemy.statuses } yield status)

  override def checkStatuses(): Unit = {
    team.foreach(_.checkStatuses())
    super.checkStatuses()
  }

  // Occasionally, an individual team member will have a status that
  // the whole team lacks as a whole.
  override def resolveStatuses(): Unit = {
    team.foreach(_.resolveStatuses())
    super.resolveStatuses()
  }

  // Behaves consistently with hasStatus; we might change that one though.
  // This should continue to behave consistently with it if it is changed.
  override def cureStatus(func: StatusEffect => Boolean): Unit = {
    super.cureStatus(func)
    team.foreach(_.cureStatus(func))
  }

  // I'm hesitant about this one. The team reports that it has an effect if ANY
  // team member has it.
  override def hasStatus(func: StatusEffect => Boolean): Boolean =
    super.hasStatus(func) || team.exists(_.hasStatus(func))

  override def afflictStatus(status: StatusEffect): Unit = status.policy match {
    case EffectPolicy.Uniform =>
      super.afflictStatus(status)
    case EffectPolicy.Distributed =>
      team.foreach { _.afflictStatus(status) }
    case EffectPolicy.FirstTarget =>
      team.headOption.foreach { _.afflictStatus(status) }
  }

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    fullTeam zip positions foreach { case (e, (x, y)) =>
      if (e.isAlive)
        e.draw(graph, rect.shift(x, y))
      super.draw(graph, rect)
    }
  }

  override def instantKill(player: Player): Unit = {
    fullTeam.foreach(_.instantKill(player))
    super.instantKill(player)
  }

  override def hasEnemy(func: Enemy => Boolean): Boolean =
    super.hasEnemy(func) || team.exists(_.hasEnemy(func))

  override def mouseOverHelp: Option[Tagged] = {
    val (x, y) = master.state.mousePosition
    // TODO The bounds on this are a bit weird since the individual enemies have
    //      an incorrect understanding of their own bounding boxes
    if (team.isEmpty)
      None
    else
      fullTeam
        .zip(positions)
        .map { case (e, (x, y)) => (e, e.rect.shift(x, y)) }
        .filter { case (e, _) => e.isAlive }
        .minBy { case (_, r) => math.max(math.abs(r.centerX - x), math.abs(r.centerY - y)) }
        ._1
        .mouseOverHelp
  }

  fullTeam.foreach(_.doNotDrawStatuses()) // This is an incredibly messy hack. I know.

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
