
package com.mercerenies.stairway
package game.content

import ui.Drawable
import game.StandardGame
import game.tagline.Tagged
import action.MouseClick

trait Content extends Drawable {

  def master: StandardGame.Master = contentArea.master

  def contentArea: ContentArea

  def step(): Unit = {}

  def click(click: MouseClick): Unit = {}

  def isIdle: Boolean = true

  def tagged: Option[Tagged] = None

}
