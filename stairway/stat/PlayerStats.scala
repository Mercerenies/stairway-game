
package com.mercerenies.stairway.stat

import com.mercerenies.stairway.game.StandardGame

class PlayerStats(val master: StandardGame.Master, val moneyChanged: (Int) => Unit) {

  private var _atkMod: Int = 0
  private var _money: Int = 10

  val levels = new ImprovableStats(master)

  def energyIncrease = levels.stamina.value

  def baseAttack: Int = levels.strength.value
  def attackModifier: Int = _atkMod
  def attackModifier_=(x: Int): Unit = {
    if (x > 0)
      _atkMod = x
    else
      _atkMod = 0
  }
  def decayAttackPower(): Unit = {
    attackModifier -= 1
  }
  def attackPower: Int = baseAttack + attackModifier

  def money: Int = _money
  def money_=(x: Int): Unit = {
    val oldMoney = _money
    if (x > 0)
      _money = x
    else
      _money = 0
    moneyChanged(_money - oldMoney)
  }
  def buy(cost: Int)(action: => Unit): Boolean = { // Subtracts money and executes the block, if it can be afforded
    if (money >= cost) {
      money -= cost
      action
      true
    } else {
      false
    }
  }

  def taxPercent: Double = levels.tax.value
  def deductTax(per: Double): Unit = {
    money -= math.ceil(money * per).toInt
  }
  def deductTax(): Unit = {
    deductTax(taxPercent)
  }

  def fadeCost: Double = math.max(50 - levels.discipline.value, 1)
  def specialAttackCost: Double = math.max(50 - levels.rage.value, 1)

  def incomeAmount: Int = 5
  def healthSpaceAmount: Double = 5.0
  def healthIncrease: Double = levels.resilience.value

  def applePrice: Int  = math.max(15 - levels.mercantilism.value, 1)
  def orangePrice: Int = math.max( 5 - levels.mercantilism.value, 1)
  def melonPrice: Int  = math.max(10 - levels.mercantilism.value, 1)

  def appleEffect: Double = levels.perserverence.value
  def orangeEffect: Double = levels.vitality.value
  def melonEffect: Double = levels.metabolism.value

  def criticalChance: Double = levels.fortune.value
  def specialMultiplier: Double = levels.force.value

  def dodgeChance: Double = levels.evasion.value

}
