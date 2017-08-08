
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import game.attack.{PlayerAttack, AttackNature, EnemyAttack, FlightLevel}
import util.tap._

class Slime(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  private var state: Slime.State = Slime.First

  override def spoils: Spoils = Spoils.Money((43 + 3 * entropy.reward).toInt)

  override def startingHealth: Double = 42 + 3 * entropy.reward

  override def attackPower: Double = state.attackPower

  override def imageIndex: Int = state.imageIndex

  override def takeDamage(attack: PlayerAttack): Unit = {
    state.nextState foreach { this.state = _ }
    super.takeDamage(attack)
  }

}

object Slime {

  sealed trait State {
    def attackPower: Double
    def imageIndex: Int
    def nextState: Option[State]
  }

  case object First extends State {
    override def attackPower = 5
    override def imageIndex = 12
    override def nextState = Some(Slime.Second)
  }

  case object Second extends State {
    override def attackPower = 10
    override def imageIndex = 13
    override def nextState = Some(Slime.Third)
  }

  case object Third extends State {
    override def attackPower = 15
    override def imageIndex = 14
    override def nextState = Some(Slime.Fourth)
  }

  case object Fourth extends State {
    override def attackPower = 20
    override def imageIndex = 15
    override def nextState = None
  }

}
