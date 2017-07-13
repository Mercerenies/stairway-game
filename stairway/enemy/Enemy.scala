
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.image.EnemiesImage
import com.mercerenies.stairway.util.{Rectangle, RandomImplicits}
import com.mercerenies.stairway.ui.Drawable
import com.mercerenies.stairway.game.{Player, StandardGame}
import com.mercerenies.stairway.game.attack.PlayerAttack
import com.mercerenies.stairway.game.content.ContentArea
import com.mercerenies.stairway.status.StatusEntity
import scala.util.Random
import java.awt.{Graphics2D, Color}

trait Enemy extends Drawable with StatusEntity {

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
    master.particleText.addParticle(f"KO!", Enemy.particleColor, rect)
  }

  def instantKill(player: Player): Unit = {
    onDeath(player)
  }

  def step(): Unit = {}

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    statuses.zipWithIndex.foreach { case (x, i) =>
      val (width, height) = x.dims
      val statusX = this.rect.xmax + width * i
      val statusY = this.rect.ymin - Player.Radius
      x.draw(graph, Rectangle(statusX, statusY, statusX + width, statusY + height))
    }
  }

  def resolveStatuses(): Unit = {
    statuses.foreach(_.perform(Right(this)))
    checkStatuses()
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
