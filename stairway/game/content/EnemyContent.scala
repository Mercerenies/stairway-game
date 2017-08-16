
package com.mercerenies.stairway.game.content

import com.mercerenies.stairway.util
import com.mercerenies.stairway.util.Rectangle
import com.mercerenies.stairway.ui.Drawable
import com.mercerenies.stairway.enemy.{EnemyBox, Enemy}
import com.mercerenies.stairway.product.{Fruits, Captioned}
import com.mercerenies.stairway.action.{MouseClick, MouseButton, ActionType}
import java.awt.{List => _, _}

class EnemyContent(contentArea: ContentArea, val enemy: EnemyBox[Enemy]) extends AbstractContent(contentArea) {

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    enemy.enemy.foreach { e => e.draw(graph, e.rect) }
  }

  override def step(): Unit = {
    enemy.refresh()
    enemy.innerEnemy.step()
  }

  override def tagged = enemy.innerEnemy.mouseOverHelp

}

