
package com.mercerenies.stairway.game

import scala.collection.mutable.{Set, HashSet}
import java.awt._
import com.mercerenies.stairway.event.{EventManager, StepEvent, ClickEvent}
import com.mercerenies.stairway.action.MouseClick

abstract class GameEntity[+T <: GameMaster](val master: T) {

  val stepEvent = new EventManager[Unit]
  val clickEvent = new EventManager[MouseClick]

  def step(): Unit = {
    stepEvent.invoke(())
  }

  def click(obj: MouseClick): Unit = {
    clickEvent.invoke(obj)
  }

  def draw(graph: Graphics2D): Unit

}
