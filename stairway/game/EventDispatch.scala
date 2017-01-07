
package com.mercerenies.stairway.game

import java.awt.Graphics2D
import com.mercerenies.stairway.action.Action

class EventDispatch(private val _gameLoop: GameLoop) {

  def draw(graph: Graphics2D) = _gameLoop drawEntities graph

  def pushAction(action: Action) = _gameLoop pushAction action
  def +=(action: Action) = _gameLoop pushAction action

}
