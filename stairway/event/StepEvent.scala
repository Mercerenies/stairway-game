
package com.mercerenies.stairway.event

trait StepEvent extends Event[Unit] {
  override final def call(_ignore: Unit): Unit = call()
  def call(): Unit
}
