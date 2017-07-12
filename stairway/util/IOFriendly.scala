
package com.mercerenies.stairway
package util

import java.io.{FileInputStream, FileOutputStream, EOFException}
import scala.reflect.ClassTag
import scala.collection.generic.CanBuildFrom
import scala.language.higherKinds

trait IOFriendly[A] {
  def write(value: A, file: IOFriendly.Writer): Unit
  def read(file: IOFriendly.Reader): A
}

object IOFriendly {

  class Writer(val file: FileOutputStream) extends AnyVal {
    def write(n: Int): Unit = { file.write(n) }
  }

  class Reader(val file: FileInputStream) extends AnyVal {
    def read(): Int = file.read() match {
      case x if x < 0 => { throw new EOFException() }
      case x => { x }
    }
  }

  def write[A](value: A, file: Writer)(implicit mgr: IOFriendly[A]): Unit = {
    mgr.write(value, file)
  }

  def read[A](file: Reader)(implicit mgr: IOFriendly[A]): A = {
    mgr.read(file)
  }

  // Necessary so that Nil and co can be serialized
  implicit object NothingIsIOFriendly extends IOFriendly[Nothing] {
    override def write(value: Nothing, file: Writer): Unit = {}
    override def read(file: Reader): Nothing = { sys.error("Nothing read from file") } // TODO Error
  }

  implicit object BoolIsIOFriendly extends IOFriendly[Boolean] {
    override def write(value: Boolean, file: Writer): Unit = {
      file.write(if (value) 0xFF else 0x00)
    }
    override def read(file: Reader): Boolean = {
      (file.read() != 0x00)
    }
  }

  implicit object CharIsIOFriendly extends IOFriendly[Char] {
    override def write(value: Char, file: Writer): Unit = {
      file.write(value.toInt)
    }
    override def read(file: Reader): Char = {
      file.read().toChar
    }
  }

  implicit object ByteIsIOFriendly extends IOFriendly[Byte] {
    override def write(value: Byte, file: Writer): Unit = {
      file.write(value.toInt)
    }
    override def read(file: Reader): Byte = {
      file.read().toByte
    }
  }

  implicit object IntIsIOFriendly extends IOFriendly[Int] {
    override def write(value: Int, file: Writer): Unit = {
      if (value < 0)
        file.write(0xFF)
      else
        file.write(0x00)
      val value1 = math.abs(value)
      for (i <- 0 until 4)
        file.write((value1 >> (i * 8)) & 0xFF)
    }
    override def read(file: Reader): Int = {
      val sign = if (file.read() == 0xFF) -1 else 1
      var number = 0
      for (i <- 0 until 4)
        number += (file.read() << (i * 8))
      number
    }
  }

  implicit object DoubleIsIOFriendly extends IOFriendly[Double] {
    override def write(value: Double, file: Writer): Unit = {
      IOFriendly.write(value.toString, file)
    }
    override def read(file: Reader): Double = {
      IOFriendly.read[String](file).toDouble
    }
  }

  implicit object StringIsIOFriendly extends IOFriendly[String] {
    override def write(value: String, file: Writer): Unit = {
      for (ch <- value)
        file.write(ch.toInt)
      file.write(0x00)
    }
    override def read(file: Reader): String = {
      var str = ""
      var curr = file.read()
      while (curr != 0x00) {
        str += curr.toChar
        curr = file.read()
      }
      str
    }
  }

  class Tuple2IsIOFriendly[A, B](val mgr1: IOFriendly[A], val mgr2: IOFriendly[B]) extends IOFriendly[(A, B)] {
    override def write(value: (A, B), file: Writer): Unit = {
      mgr1.write(value._1, file)
      mgr2.write(value._2, file)
    }
    override def read(file: Reader): (A, B) = {
      val a = mgr1.read(file)
      val b = mgr2.read(file)
      (a, b)
    }
  }

  implicit def tuple2IsIOFriendly[A, B](implicit a: IOFriendly[A], b: IOFriendly[B]): IOFriendly[(A, B)] =
    new Tuple2IsIOFriendly(a, b)

  // This somewhat odd workaround is necessitated by https://issues.scala-lang.org/browse/SI-9453,
  // a bug in Scala which seems to cause issues with Nothing when used in type argument inference.
  type IOEmptySeq = List[Int]
  def IOEmptySeq = List[Int]()

  class SeqIsIOFriendly[A, F[A] <: Seq[A]](
    val mgr: IOFriendly[A],
    val builder: CanBuildFrom[Nothing, A, F[A]])
      extends IOFriendly[F[A]] {
    override def write(value: F[A], file: Writer): Unit = {
      IOFriendly.write(value.size, file)
      for (x <- value.iterator) {
        mgr.write(x, file)
      }
    }
    override def read(file: Reader): F[A] = {
      val len = IOFriendly.read[Int](file)
      val b = builder()
      for (i <- 1 to len) {
        b += mgr.read(file)
      }
      b.result()
    }
  }

  implicit def seqIsIOFriendly[A, F[A] <: Seq[A]](implicit m: IOFriendly[A], b: CanBuildFrom[Nothing, A, F[A]]):
      IOFriendly[F[A]] = new SeqIsIOFriendly(m, b)

}
