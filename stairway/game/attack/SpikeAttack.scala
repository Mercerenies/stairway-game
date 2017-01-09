
package com.mercerenies.stairway.game.attack

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.enemy.{Enemy, HealthBased}
import java.awt.Color

class SpikeAttack(master: StandardGame.Master, override val damage: Double) extends PlayerAttack(master)
