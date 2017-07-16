
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.Player
import com.mercerenies.stairway.product.Fruits
import com.mercerenies.stairway.product.item
import com.mercerenies.stairway.stat.ImprovableStats

case class Spoils(award: Player => Unit) {

  def giveTo(player: Player) = award(player)

  def +(spoils: Spoils) = Spoils { player =>
    this.award(player)
    spoils.award(player)
  }

}

object Spoils {

  object None extends Spoils({ player => {}})

  object Money {
    def apply(n: Int) = Spoils { player =>
      player.master.stats.money += n
    }
  }

  object Item {
    def apply(x: item.Item) = Spoils { player =>
      player.master.inventory.addItem(x)
    }
  }

  object Apple extends Spoils({ player => Fruits.apple.giveTo(player) })

  object Orange extends Spoils({ player => Fruits.orange.giveTo(player) })

  object Melon extends Spoils({ player => Fruits.melon.giveTo(player) })

  object Strength extends Spoils({ player => player.master.stats.levels.strength.buff() })

  object Resilience extends Spoils({ player => player.master.stats.levels.resilience.buff(5) })

}
