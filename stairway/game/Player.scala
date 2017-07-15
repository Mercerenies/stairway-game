
package com.mercerenies.stairway.game

import com.mercerenies.stairway.space.Space
import com.mercerenies.stairway.util.{Rectangle, Index}
import com.mercerenies.stairway.game.belt.ConveyerBelt
import com.mercerenies.stairway.status.StatusEntity
import java.awt._
import java.awt.geom.Ellipse2D

class Player(master: StandardGame.Master, val xPos: Int)
    extends GameEntity[StandardGame.Master](master) with StatusEntity {

  var faded = false

  val occupiedSpace = Player.DefaultOccupiedSpace

  def offsetFromBottom: Double = master.roomHeight - 2 * ConveyerBelt.SpaceDim.height

  def drawColor: Color = if (faded) Color.lightGray else Color.black

  def occupiedPosition: Index = master.belt.RelativeIndex(occupiedSpace)

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

  def takeDamage(damage: Double): Unit = {
    master.meter.health.value -= damage
    // TODO Check for survival
  }

  def resolveStatuses(): Unit = {
    statuses.foreach(_.perform(Left(this)))
    checkStatuses()
  }

}

object Player {
  val Radius = 12
  val DefaultOccupiedSpace = 2
}
