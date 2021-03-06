
package com.mercerenies.stairway
package game.content

import event.{StepEvent, ClickEvent}
import action.MouseClick
import game.{GameEntity, StandardGame}
import game.tagline.Tagged
import util.Rectangle
import java.awt._

class ContentArea(master: StandardGame.Master, val rect: Rectangle)
    extends GameEntity[StandardGame.Master](master) {

  private object StepDelegator extends StepEvent {
    override def call(): Unit = {
      for (
        c <- content
      ) {
        c.step()
      }
    }
  }

  private object ClickDelegator extends ClickEvent {
    override def call(click: MouseClick): Unit = {
      for (
        c <- content
      ) {
        c.click(click)
      }
    }
  }

  private var _content: Option[Content] = None

  def content: Option[Content] = _content

  def taggedContent: Option[Tagged] =
    for {
      c <- content
      t <- c.tagged
    } yield t

  def put(obj: Content): Unit = {
    _content = Some(obj)
  }

  def clear(): Unit = {
    _content = None
  }

  def isIdle: Boolean = content match {
    case None => true
    case Some(x) => x.isIdle
  }

  override def draw(graph: Graphics2D): Unit = {
    for (
      c <- _content
    ) {
      c.draw(graph, rect)
    }
  }

  stepEvent += StepDelegator
  clickEvent += ClickDelegator

}
