
package com.mercerenies.stairway
package enemy

trait TurnCounterEnemy extends Enemy {

  private var _counter = 0

  def counter: Int = _counter

  def isEndOfCycle(cycleLength: Int): Boolean =
    counter % cycleLength == cycleLength - 1

  def advanceCounter(): Unit = {
    _counter += 1
  }

}
