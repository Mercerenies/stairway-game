
package com.mercerenies.stairway.event

trait Event[-T] {
  def call(value: T)
}
