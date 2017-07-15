
package com.mercerenies.stairway
package enemy

trait TurnCounterEnemy extends Enemy {

  private var _counter = counterStart

  def counter: Int = _counter

  def counterStart: Int = 0

  def isEndOfCycle(cycleLength: Int): Boolean =
    counter % cycleLength == cycleLength - 1

  def advanceCounter(): Unit = {
    _counter += 1
  }

}
