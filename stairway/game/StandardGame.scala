
package com.mercerenies.stairway
package game

import image.ButtonsImage
import stat.PlayerStats
import util.{Rectangle, Index}
import luck.{DiceValue, PlayerLuck}
import debug.DebugBox
import game.content._
import game.belt._
import game.world._
import space._
import product.item._
import enemy._
import scala.util.control
import scala.collection.breakOut
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
    val saveload = new SaveLoader(this)

    override lazy val objects = List(
      belt, contentArea,
      player, inventory, damage, meter, buttonPad,
      particleText, statusBar, saveload, debugger
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

    def stepForward(n: Int = 1): Unit = {
      player.currentSpace onDepart this
      belt.animatedShift(n) {
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

    def leapForward(): Unit = {
      belt.shiftBottom(ConveyerBelt.SpaceDim.height)
      player.currentSpace onEmulate this
      player.resolveStatuses()
      player.faded = false
      silently {
        meter.health.value += stats.healthIncrease
        meter.energy.value += stats.energyIncrease
      }
      stats.decayAttackPower()
    }

    def spacesMoved: Int =
      belt.bottomIndex + player.occupiedSpace

    def era: Int = _era

    def advanceEra(): Unit = {
      _era += 1
      system.eraChanged(era)
    }

    def mirror: GameData =
      GameData(
        _era,
        stats.attackModifier,
        stats.money,
        stats.levels.luck.value,
        stats.levels.strength.value,
        stats.levels.perseverence.value,
        stats.levels.vitality.value,
        stats.levels.metabolism.value,
        stats.levels.mercantilism.value,
        stats.levels.stamina.value,
        stats.levels.discipline.value,
        stats.levels.rage.value,
        stats.levels.chaos.value,
        stats.levels.tax.value,
        stats.levels.fortune.value,
        stats.levels.force.value,
        stats.levels.resilience.value,
        stats.levels.evasion.value,
        player.faded,
        luck.karma,
        meter.energy.max.toDouble,
        meter.energy.value.toDouble,
        meter.health.max.toDouble,
        meter.health.value.toDouble,
        buttonPad.appleButton.count,
        buttonPad.orangeButton.count,
        buttonPad.melonButton.count,
        inventory.capacity,
        inventory.toList,
        belt.bottomIndex + player.occupiedSpace,
        damage.horizontalShift,
        system.mirror,
        stats.levels.standardUpgrades.map(_.timesBought)(breakOut)
      )

    def unmirror(data: GameData) = {
      // Do this at the beginning to the generators reset around it
      _era = data.era
      system.freezeBase()
      system.unmirror(data.belt)
      belt.putIndex(data.playerSpace - player.occupiedSpace)
      system.resetState()

      stats.attackModifier = data.atkMod
      stats.money = data.money
      stats.levels.luck.value = data.luck
      stats.levels.strength.value = data.strength
      stats.levels.perseverence.value = data.perseverence
      stats.levels.vitality.value = data.vitality
      stats.levels.metabolism.value = data.metabolism
      stats.levels.mercantilism.value = data.mercantilism
      stats.levels.stamina.value = data.stamina
      stats.levels.discipline.value = data.discipline
      stats.levels.rage.value = data.rage
      stats.levels.chaos.value = data.chaos
      stats.levels.tax.value = data.tax
      stats.levels.fortune.value = data.fortune
      stats.levels.force.value = data.force
      stats.levels.resilience.value = data.resilience
      stats.levels.evasion.value = data.evasion
      player.faded = data.faded
      luck.karma = data.karma
      meter.energy.max = data.maxEnergy
      meter.energy.value = data.energy
      meter.health.max = data.maxHealth
      meter.health.value = data.health
      buttonPad.appleButton.count = data.apples
      buttonPad.orangeButton.count = data.oranges
      buttonPad.melonButton.count = data.melons
      inventory.capacity = data.invSize
      inventory.clear()
      data.invData.foreach { inventory.addItem(_) }
      damage.horizontalShift = data.damageShift
      data.upgradeBuys zip stats.levels.standardUpgrades foreach { case (n, x) => x.timesBought = n }
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
