
package com.mercerenies.stairway
package game.attack

sealed trait FlightLevel

object FlightLevel {

  def apply(flying: Boolean): FlightLevel =
    if (flying) Flying else Grounded

  case object Grounded extends FlightLevel
  case object Special extends FlightLevel
  case object Flying extends FlightLevel

  val DamageFactor = 0.5

}
