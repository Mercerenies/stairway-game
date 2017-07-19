
package com.mercerenies.stairway
package status

sealed trait EffectPolicy

object EffectPolicy {

  case object Uniform     extends EffectPolicy
  case object Distributed extends EffectPolicy
  case object FirstTarget extends EffectPolicy

}
