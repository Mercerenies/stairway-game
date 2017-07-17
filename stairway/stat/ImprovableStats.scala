
package com.mercerenies.stairway.stat

import com.mercerenies.stairway.game.{StandardGame, Player}
import com.mercerenies.stairway.product.Purchasable
import scala.collection.immutable.Vector

class ImprovableStats(val master: StandardGame.Master) {
  import ImprovableStats.{Stat, UpgradeSlot}

  val health = new Stat("Health", "Capacity of health meter", 100.0, 5.0, Some(0.0)) {
    override def onChanged(amt: Double): Unit = {
      master.meter.health.max += amt
    }
  }

  val energy = new Stat("Energy", "Capacity of energy meter", 100.0, 5.0, Some(0.0)) {
    override def onChanged(amt: Double): Unit = {
      master.meter.energy.max += amt
    }
  }

  val luck          = new Stat("Luck", "Chance of success in luck events", 1.0, 0.05, Some(0.0))
  val strength      = new Stat("Strength", "Base offensive power against enemies", 2, 1, Some(0))
  val perseverence  = new Stat("Perseverence", "Effect of eating apples", 15.0, 1.0, Some(0.0))
  val vitality      = new Stat("Vitality", "Effect of eating oranges", 10.0, 2.0, Some(0.0))
  val metabolism    = new Stat("Metabolism", "Effect of eating melons", 12.0, 2.0, Some(0.0))
  val mercantilism  = new Stat("Mercantilism", "Quality of prices at shops", 0, 1, None)
  val stamina       = new Stat("Stamina", "Restore rate of energy meter", 5.0, 0.5, Some(0.0))
  val discipline    = new Stat("Discipline", "Cost efficiency of fade ability", 0.0, 2.0, Some(0.0))
  val rage          = new Stat("Rage", "Cost efficiency of special attack", 0.0, 2.0, Some(0.0))
  val chaos         = new Stat("Chaos", "Effect of karma on luck", 1.0, 1.0, Some(0.0))
  val tax           = new Stat("Tax", "Money taken on tax spaces", 0.15, -0.01, Some(0.0))
  val fortune       = new Stat("Fortune", "Chance of critical hits", 0.01, 0.005, Some(0.0))
  val force         = new Stat("Force", "Power of special attack", 2.0, 0.05, Some(0.0))
  val resilience    = new Stat("Resilience", "Health restored each turn", 0.0, 0.1, Some(0.0))
  val evasion       = new Stat("Evasion", "Chance of dodging attacks", 0.01, 0.005, Some(0.0))

  lazy val standardUpgrades: Seq[UpgradeSlot[_]] = Vector(
    new UpgradeSlot(health       ,  5, 10),
    new UpgradeSlot(energy       ,  5, 10),
    new UpgradeSlot(luck         ,  5,  5),
    new UpgradeSlot(perseverence , 10, 10),
    new UpgradeSlot(vitality     , 10, 10),
    new UpgradeSlot(metabolism   , 10, 10),
    new UpgradeSlot(mercantilism , 10, 20),
    new UpgradeSlot(stamina      ,  5, 10),
    new UpgradeSlot(discipline   ,  5,  5),
    new UpgradeSlot(rage         ,  5,  5),
    new UpgradeSlot(tax          , 10,  5),
    new UpgradeSlot(fortune      , 25, 10),
    new UpgradeSlot(force        ,  5,  5),
    new UpgradeSlot(evasion      , 25, 10)
  )

}

object ImprovableStats {

  class Stat[T : Numeric](
    val name: String,
    val description: String,
    private var amount: T,
    val improveAmount: T,
    val minValue: Option[T] = None) {

    import Ordering.Implicits._
    import Numeric.Implicits._

    def value: T = amount

    def value_=(v: T) = {
      amount = v
      buffBy(implicitly[Numeric[T]].fromInt(0))
    }

    def onChanged(amt: T): Unit = {}

    def buffBy(amt: T): Unit = {
      amount += amt
      minValue match {
        case Some(x) if amount < x =>
          amount = x
        case _ => {}
      }
      onChanged(amt: T)
    }

    def buff(n: Int): Unit = {
      val n1 = implicitly[Numeric[T]].fromInt(n)
      buffBy(improveAmount * n1)
    }

    def buff(): Unit = buff(1)

    def debuff(): Unit = buff(-1)

  }

  class UpgradeSlot[T](val stat: Stat[T], val basePrice: Int, val priceUp: Int) extends Purchasable {

    private var _timesBought = 0

    override def price(player: Player): Int = basePrice + timesBought * priceUp

    override def giveTo(player: Player): Unit = {
      timesBought += 1
      stat.buff()
    }

    def timesBought: Int = _timesBought

    def timesBought_=(x: Int) = {
      _timesBought = x
      if (_timesBought < 0)
        _timesBought = 0
    }

  }

}
