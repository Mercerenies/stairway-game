
package com.mercerenies.stairway.game.content

import com.mercerenies.stairway.ui.Drawable
import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.action.MouseClick

trait Content extends Drawable {

  def master: StandardGame.Master = contentArea.master

  def contentArea: ContentArea

  def step(): Unit = {}

  def click(click: MouseClick): Unit = {}

  def isIdle: Boolean = true

}
