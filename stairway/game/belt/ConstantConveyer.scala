
package com.mercerenies.stairway.game.belt

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.space.Space
import com.mercerenies.stairway.util.Index

class ConstantConveyer(master: StandardGame.Master, val space: Space) extends CyclicConveyer(master, space)
