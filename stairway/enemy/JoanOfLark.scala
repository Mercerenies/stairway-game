
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}

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

  private def spreadLength: Int = health match {
    case x if x < 25.0 => 8
    case x if x < 50.0 => 5
    case _             => 4
  }

  override def counterStart = 0

  override def attack(player: Player): Unit = {
    //// TODO ALL OF THIS AND WINGED AND WHATNOT
    super.attack(player)
    advanceCounter()
  }

}

