
package com.mercerenies.stairway.util

import scala.collection.mutable.ListBuffer
import java.util.concurrent.ConcurrentLinkedQueue

class ThreadSafeQueue[T] {

  private val queue = new ConcurrentLinkedQueue[T]

  def enqueue(x: T): Unit = queue add x
  def +=(x: T) = {
    this enqueue x
    this
  }
  def dequeue() = Option(queue.poll())
  def head = Option(queue.peek())
  def isEmpty = head.isEmpty

  def popAll(): Seq[T] = {
    val accum = new ListBuffer[T]
    Functional.recurse((g: () => Unit) => {
      dequeue() match {
        case None => {}
        case Some(x) => {
          accum += x
          g()
        }
      }
    })
    accum.toList
  }

}
