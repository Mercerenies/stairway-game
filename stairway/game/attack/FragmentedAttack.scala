
package com.mercerenies.stairway.game.attack

import com.mercerenies.stairway.game.{StandardGame, Player}
import com.mercerenies.stairway.enemy.Enemy

class FragmentedAttack(attack: PlayerAttack, val parts: Int)
    extends MagnifiedAttack(attack, 1.0 / parts)
