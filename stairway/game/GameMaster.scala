
package com.mercerenies.stairway.game

import java.awt.Graphics2D

abstract class GameMaster {

  val gameLoop = new GameLoop(this)

  def objects: Traversable[GameEntity[GameMaster]]
  def state = gameLoop.state

  def roomSize = gameLoop.roomSize
  def roomWidth = gameLoop.roomWidth
  def roomHeight = gameLoop.roomHeight

  def step(): Unit = {
    for (elem <- objects)
      elem.step()
  }

  def draw(graph: Graphics2D): Unit = {
    for (elem <- objects)
      elem draw graph
  }

}
