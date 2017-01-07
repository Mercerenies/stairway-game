
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case class RedSpace(severity: RedSpace.Severity) extends ImageSpace {

  override def imageIndex: Int = severity.index

  def damage: Double = severity.damage

  override def onLand(master: StandardGame.Master) = {
    master.meter.health.value -= damage
  }

}

object RedSpace {

  sealed trait Severity {
    def index: Int
    def damage: Double
  }

  case object Single extends Severity {
    override def index = 1
    override def damage = 6
  }

  case object Double extends Severity {
    override def index = 4
    override def damage = 24
  }

  case object Triple extends Severity {
    override def index = 5
    override def damage = 96
  }

  case object Quadruple extends Severity {
    override def index = 16
    override def damage = 384
  }

}
