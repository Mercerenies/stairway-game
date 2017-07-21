
package com.mercerenies.stairway
package enemy

import image.EnemiesImage
import util.{Rectangle, RandomImplicits}
import ui.Drawable
import game.{Player, StandardGame}
import game.attack.PlayerAttack
import game.content.ContentArea
import status.{StatusEntity, StatusEffect}
import scala.util.Random
import java.awt.{Graphics2D, Color}

trait Enemy extends Drawable with StatusEntity {

  private var _drawStatuses = true

  def master: StandardGame.Master
  def contentArea = master.contentArea

  def spoils: Spoils

  def isUndead: Boolean = false

  def isBoss: Boolean = false

  def rect: Rectangle = {
    val fullRect = contentArea.rect
    val width = EnemiesImage.Width
    val height = EnemiesImage.Height
    Rectangle(
      fullRect.centerX - width  / 2.0,
      fullRect.centerY - height / 2.0,
      fullRect.centerX + width  / 2.0,
      fullRect.centerY + height / 2.0
    )
  }

  def isAlive: Boolean

  def attack(player: Player): Unit

  def takeDamage(attack: PlayerAttack): Unit = {
    if (!isAlive) {
      onDeath(attack.player)
    }
  }

  def onDeath(player: Player): Unit = {
    spoils.giveTo(player)
  }

  def instantKill(player: Player): Unit = {
    onDeath(player)
  }

  def hasEnemy(func: Enemy => Boolean): Boolean =
    func(this)

  def step(): Unit = {}

  def allStatuses: List[StatusEffect] =
    statuses

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    if (_drawStatuses) {
      allStatuses.zipWithIndex.foreach { case (x, i) =>
        val (width, height) = x.dims
        val statusX = this.rect.xmax + width * i
        val statusY = this.rect.ymin
        x.draw(graph, Rectangle(statusX, statusY, statusX + width, statusY + height))
      }
    }
  }

  def resolveStatuses(): Unit = {
    statuses.foreach(_.perform(Right(this)))
    checkStatuses()
  }

  def doNotDrawStatuses(): Unit = {
    _drawStatuses = false
  }

}

object Enemy {

  import RandomImplicits._

  val image = new EnemiesImage

  val particleColor = Color.red.darker

  case class Entropy(risk: Double, reward: Double)

  def noEntropy = Entropy(0, 0)

  def entropy(rand: Random) = {
    val poss: List[Double] = List(-1, -1, 0, 0, 1)
    val risk = rand.nextOf(poss: _*)
    Entropy(risk, risk)
  }

}
