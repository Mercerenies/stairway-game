
package com.mercerenies.stairway.game

import com.mercerenies.stairway.image.ButtonsImage
import com.mercerenies.stairway.stat.PlayerStats
import com.mercerenies.stairway.util.{Rectangle, Index}
import com.mercerenies.stairway.luck.{DiceValue, PlayerLuck}
import com.mercerenies.stairway.game.content._
import com.mercerenies.stairway.game.belt._
import com.mercerenies.stairway.game.world._
import com.mercerenies.stairway.space._
import com.mercerenies.stairway.product.item._
import com.mercerenies.stairway.enemy._
import scala.util.control
import javax.swing.SwingUtilities
import java.awt.Color

object StandardGame {

  val BeltRightMargin = 150
  val PlayerMeterWidth = 100
  val PlayerMeterX = 510

  class Master extends GameMaster {

    private var _era: Int = 1
    private var _silent: Boolean = false

    val stats = new PlayerStats(this, moneyChanged _)
    val luck = new PlayerLuck(this)

    val debugger = new DebugBox(this)
    val player = new Player(this, BeltRightMargin / 2)
    val statusBar = new StatusBar(this)
    val meter = new PlayerMeter(this, PlayerMeterX, 20, PlayerMeterWidth)
    val buttonPad = new ButtonPad(this, (BeltRightMargin + 20, 440 - ButtonsImage.Height * 3))
    val particleText = new ParticleText(this)
    val contentArea = new ContentArea(this, Rectangle(BeltRightMargin + 32, 40, 620, 370))
    val inventory = new Inventory(this, BeltRightMargin + 180, 370)
    val system = new BeltSystem(this)
    val belt: ConveyerBelt[OverrideConveyer[ConveyerFeed]] =
      new ConveyerBelt(this, system.belt, BeltRightMargin)
    val damage = new StandardDamage(this, Rectangle(BeltRightMargin + 10, 5, PlayerMeterX - 10, 50))

    override lazy val objects = List(
      belt, contentArea,
      player, inventory, damage, meter, buttonPad,
      particleText, statusBar, debugger
    )

    private def moneyChanged(amount: Int): Unit = {
      val sign = if (amount >= 0) "+" else "-"
      val text = s"$sign$$${math.abs(amount)}"
      if (!silent)
        particleText.addParticle(text, Color.green.darker, player.drawRect, (-90.0, 45.0))
    }

    def silent: Boolean = _silent

    def silently[U](arg: => U): U = {
      val block = control.Exception.ultimately {
        _silent = false
      }
      _silent = true
      block {
        arg
      }
    }

    def isIdle = (!belt.isMoving) && (contentArea.isIdle)

    def isInCombat: Boolean = !currentEnemy.isEmpty

    def currentEnemyBox: Option[EnemyBox[Enemy]] = contentArea.content match {
      case Some(content: EnemyContent) => Some(content.enemy)
      case _ => None
    }

    def currentEnemy: Option[Enemy] =
      for (
        b <- currentEnemyBox;
        e <- b.enemy
      ) yield e

    def stepForward(): Unit = {
      player.currentSpace onDepart this
      belt.animatedShift(1) {
        player.currentSpace onLand this
        player.resolveStatuses()
      }
      player.faded = false
      silently {
        meter.health.value += stats.healthIncrease
        meter.energy.value += stats.energyIncrease
      }
      stats.decayAttackPower()
    }

    def canFadeForward: Boolean = meter.energy.value.toDouble >= stats.fadeCost

    def fadeForward(): Unit = {
      if (canFadeForward) {
        player.currentSpace onDepart this
        belt.animatedShift(1) {}
        player.faded = true
        silently {
          meter.health.value += stats.healthIncrease
          meter.energy.value -= stats.fadeCost
        }
        stats.decayAttackPower()
      }
    }

    def spacesMoved: Int =
      belt.bottomIndex + player.occupiedSpace

    def era: Int = _era

    def advanceEra(): Unit = {
      _era += 1
      system.eraChanged(era)
    }

    inventory.addItem(DivineBolt)

  }

  def start(): Master = {
    val master = new Master
    SwingUtilities.invokeLater(new Runnable {
      override def run() = master.gameLoop.start()
    })
    master
  }

}
