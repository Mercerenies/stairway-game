
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import game.attack.{EnemyAttack, AttackNature, FlightLevel}
import game.tagline.Tagged
import util.PointImplicits._

abstract class SingleEnemy(override val master: StandardGame.Master)
    extends ImageEnemy
    with HealthBased
    with Tagged {
  import SingleEnemy._

  var defeatText: String = "KO!"

  def attackPower: Double

  def netAttackPower: Double = {
    statuses.map(_.attackModifier).foldLeft(attackPower) { _ + _ }
  }

  override def onDeath(player: Player): Unit = {
    super.onDeath(player)
    master.particleText.addParticle(defeatText, Enemy.particleColor, rect)
  }

  override def attack(player: Player): Option[Double] = {
    if (player.master.luck.evaluateLuck((LuckWeightMinus, LuckWeightPlus), master.stats.dodgeChance)) {
      master.particleText.addParticle(
        f"MISS!",
        HealthBased.particleColor,
        player.drawRect,
        (-90.0, 45.0)
      )
      None
    } else {
      val dmg = player.takeDamage(EnemyAttack(netAttackPower, AttackNature(FlightLevel(isFlying))))
      Some(dmg)
    }
  }

  override def mouseOverHelp =
    if (master.state.mousePosition within rect)
      Some(this)
    else
      None

  override def tagText = Some(s"$name\n$desc")

  def name: String

  def desc: String

}

object SingleEnemy {
  val LuckWeightMinus = 0.09
  val LuckWeightPlus = 0.001
}
