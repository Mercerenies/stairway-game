
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import status.WingedEffect

class JoanOfLark(master: StandardGame.Master)
    extends SingleEnemy(master)
    with BossEnemy
    with TurnCounterEnemy {

  override def bossName: String = "Joan of Lark"

  override def spoils: Spoils =
    Spoils.Money(115) + Spoils.Strength + Spoils.Resilience + Spoils.InvSlot

  override def startingHealth: Double = 130.0

  override def attackPower: Double = 13.0

  override def imageIndex: Int = 23

  override def counterStart = 0

  override def attack(player: Player): Unit = {
    if (hasStatus(_.isInstanceOf[WingedEffect])) {
      resetCounter()
      super.attack(player)
    } else {
      if (isEndOfCycle(JoanOfLark.attackCycle))
        afflictStatus(new WingedEffect(None))
      else
        super.attack(player)
      advanceCounter()
    }
    ///// Secondary attack? Or no?
  }

  afflictStatus(new WingedEffect(None))

}

object JoanOfLark {
  val attackCycle = 6
}
