
package com.mercerenies.stairway
package game

import space.{Space, RedSpace}
import util.{Rectangle, Index}
import game.belt.ConveyerBelt
import status.StatusEntity
import attack.{EnemyAttack, FlightLevel}
import product.item.Charm
import java.awt._
import java.awt.geom.Ellipse2D

class Player(master: StandardGame.Master, val xPos: Int)
    extends GameEntity[StandardGame.Master](master) with StatusEntity {

  var faded = false

  val occupiedSpace = Player.DefaultOccupiedSpace

  def offsetFromBottom: Double = master.roomHeight - 2 * ConveyerBelt.SpaceDim.height

  def drawColor: Color = if (faded) Color.lightGray else Color.black

  def occupiedPosition: Index = master.belt.RelativeIndex(occupiedSpace)

  def nextPositions: Seq[Index] = {
    val playerPos = occupiedPosition
    Stream from 1 map { playerPos + _ }
  }

  def nextNPositions(n: Int): Seq[Index] = {
    val playerPos = occupiedPosition
    1 to n map { playerPos + _ }
  }

  def currentSpace: Space = forwardSpace(0)

  def nextSpace: Space = forwardSpace(1)

  def forwardSpace(n: Int): Space = {
    val belt = master.belt
    belt.getSpace(occupiedPosition + n)
  }

  def drawRect: Rectangle = {
    val radius = Player.Radius
    val yPos = offsetFromBottom
    Rectangle(xPos - radius, yPos - radius, xPos + radius, yPos + radius)
  }

  override def draw(graph: Graphics2D): Unit = {
    val radius = Player.Radius
    val yPos = offsetFromBottom
    graph.setColor(drawColor)
    graph.fill(new Ellipse2D.Double(xPos - radius, yPos - radius, radius * 2, radius * 2))
    graph.setColor(Color.white)
    graph.draw(new Ellipse2D.Double(xPos - radius, yPos - radius, radius * 2, radius * 2))
    statuses.zipWithIndex.foreach { case (x, i) =>
      val (width, height) = x.dims
      val statusX = xPos + Player.Radius + width * i
      val statusY = yPos - Player.Radius
      x.draw(graph, Rectangle(statusX, statusY, statusX + width, statusY + height))
    }
  }

  def takeDamage(attack: EnemyAttack): Double = {
    val dmg =
      if (this.isFlying && attack.nature.flight == FlightLevel.Grounded)
        attack.damage * FlightLevel.DamageFactor
      else
        attack.damage
    master.meter.health.value -= dmg
    // TODO Check for survival
    dmg
  }

  def resolveStatuses(): Unit = {
    statuses.foreach(_.perform(Left(this)))
    checkStatuses()
  }

  def isProtectedAgainst(space: RedSpace.Severity): Boolean =
    master.inventory.toList.exists {
      case charm: Charm => charm.isProtectedAgainst(space)
      case _ => false
    }

}

object Player {
  val Radius = 12
  val DefaultOccupiedSpace = 2
}
