
package com.mercerenies.stairway
package game

import product.item.Item
import util.{IOFriendly, Index}

// Data class, used to serialize the game's data
case class GameData(
  era: Int,
  atkMod: Int,
  money: Int,
  luck: Double,
  strength: Int,
  perseverence: Double,
  vitality: Double,
  metabolism: Double,
  mercantilism: Int,
  stamina: Double,
  discipline: Double,
  rage: Double,
  chaos: Double,
  tax: Double,
  fortune: Double,
  force: Double,
  resilience: Double,
  evasion: Double,
  faded: Boolean,
  karma: Double,
  maxEnergy: Double,
  energy: Double,
  maxHealth: Double,
  health: Double,
  apples: Int,
  oranges: Int,
  melons: Int,
  invSize: Int,
  invData: List[Item],
  playerSpace: Int,
  damageShift: Double,
  belt: GameData.Belt,
  upgradeBuys: List[Int],
  rootEnergy: Double
)

object GameData {

  case class ScrollState(
    age: Int,
    used: (Boolean, Boolean, Boolean)
  )

  object ScrollState {
    val Default = ScrollState(0, (false, false, false))
  }

  case class Belt(
    double: Int,
    triple: Int,
    quadruple: Int,
    dojoIndex: Int,
    scrolls: List[ScrollState],
    lastSwitch: Index.Type
  )

  implicit object ScrollStateIsIOFriendly extends IOFriendly[ScrollState] {
    override def write(value: ScrollState, file: IOFriendly.Writer): Unit = {
      IOFriendly.write(value.age, file)
      IOFriendly.write("", file)
      IOFriendly.write(value.used._1, file)
      IOFriendly.write(value.used._2, file)
      IOFriendly.write(value.used._3, file)
      IOFriendly.write(false, file)
      IOFriendly.write(false, file)
    }
    override def read(file: IOFriendly.Reader): ScrollState = {
      val age = IOFriendly.read[Int](file)
      IOFriendly.read[String](file)
      val u1 = IOFriendly.read[Boolean](file)
      val u2 = IOFriendly.read[Boolean](file)
      val u3 = IOFriendly.read[Boolean](file)
      IOFriendly.read[Boolean](file)
      IOFriendly.read[Boolean](file)
      ScrollState(age, (u1, u2, u3))
    }
  }

  implicit object BeltIsIOFriendly extends IOFriendly[Belt] {
    override def write(value: Belt, file: IOFriendly.Writer): Unit = {
      IOFriendly.write(value.double, file)
      IOFriendly.write(value.triple, file)
      IOFriendly.write(value.quadruple, file)
      IOFriendly.write(0.0, file)
      IOFriendly.write(0.0, file)
      IOFriendly.write(value.dojoIndex, file)
      IOFriendly.write("", file)
      IOFriendly.write(value.scrolls, file)
      IOFriendly.write(value.lastSwitch, file)
      IOFriendly.write(0, file)
      IOFriendly.write(0, file)
    }
    override def read(file: IOFriendly.Reader): Belt = {
      val double = IOFriendly.read[Int](file)
      val triple = IOFriendly.read[Int](file)
      val quadruple = IOFriendly.read[Int](file)
      IOFriendly.read[Double](file)
      IOFriendly.read[Double](file)
      val dojo = IOFriendly.read[Int](file)
      IOFriendly.read[String](file)
      val scrolls = IOFriendly.read[List[ScrollState]](file)
      val lastSwitch = IOFriendly.read[Index.Type](file)
      IOFriendly.read[Int](file)
      IOFriendly.read[Int](file)
      Belt(double, triple, quadruple, dojo, scrolls, lastSwitch)
    }
  }

  implicit object GameDataIsIOFriendly extends IOFriendly[GameData] {
    override def write(value: GameData, file: IOFriendly.Writer): Unit = {
      import value._

      // Era and basic information
      IOFriendly.write(era, file)
      IOFriendly.write(0, file)

      // Player stats
      IOFriendly.write(atkMod, file)
      IOFriendly.write(money, file)
      IOFriendly.write(0.0, file)
      IOFriendly.write(0.0, file)

      // Improvable stats
      IOFriendly.write(luck, file)
      IOFriendly.write(strength, file)
      IOFriendly.write(perseverence, file)
      IOFriendly.write(vitality, file)
      IOFriendly.write(metabolism, file)
      IOFriendly.write(mercantilism, file)
      IOFriendly.write(stamina, file)
      IOFriendly.write(discipline, file)
      IOFriendly.write(rage, file)
      IOFriendly.write(chaos, file)
      IOFriendly.write(tax, file)
      IOFriendly.write(fortune, file)
      IOFriendly.write(force, file)
      IOFriendly.write(resilience, file)
      IOFriendly.write(evasion, file)
      IOFriendly.write(0, file)
      IOFriendly.write(0, file)
      IOFriendly.write(0, file)
      IOFriendly.write(rootEnergy, file)
      IOFriendly.write(0.0, file)
      IOFriendly.write(0.0, file)
      IOFriendly.write("", file)
      IOFriendly.write("", file)

      // State of space
      IOFriendly.write(faded, file)
      IOFriendly.write(false, file)

      // Karma
      IOFriendly.write(karma, file)

      // Meters
      IOFriendly.write(maxEnergy, file)
      IOFriendly.write(energy, file)
      IOFriendly.write(0.0, file)
      IOFriendly.write(maxHealth, file)
      IOFriendly.write(health, file)
      IOFriendly.write(0.0, file)
      IOFriendly.write(0.0, file)
      IOFriendly.write(0.0, file)
      IOFriendly.write(0.0, file)
      IOFriendly.write(upgradeBuys, file)

      // Fruits
      IOFriendly.write(apples, file)
      IOFriendly.write(oranges, file)
      IOFriendly.write(melons, file)
      IOFriendly.write(0, file)
      IOFriendly.write(0, file)

      // Inventory
      IOFriendly.write(invSize, file)
      IOFriendly.write(0, file)
      IOFriendly.write(invData, file)
      IOFriendly.write(IOFriendly.IOEmptySeq, file)

      // Current Space
      IOFriendly.write(playerSpace, file)
      IOFriendly.write(0, file)
      IOFriendly.write(0, file)

      // Alignment
      IOFriendly.write(damageShift, file)
      IOFriendly.write(0.0, file)

      // Etc
      IOFriendly.write(belt, file)
      IOFriendly.write("", file)
      IOFriendly.write("", file)
      IOFriendly.write("", file)
      IOFriendly.write(IOFriendly.IOEmptySeq, file)
      IOFriendly.write(IOFriendly.IOEmptySeq, file)
      IOFriendly.write(0, file)

    }
    override def read(file: IOFriendly.Reader): GameData = {

      val era = IOFriendly.read[Int](file)
      IOFriendly.read[Int](file)

      val atkMod = IOFriendly.read[Int](file)
      val money = IOFriendly.read[Int](file)
      IOFriendly.read[Double](file)
      IOFriendly.read[Double](file)

      val luck = IOFriendly.read[Double](file)
      val strength = IOFriendly.read[Int](file)
      val perseverence = IOFriendly.read[Double](file)
      val vitality = IOFriendly.read[Double](file)
      val metabolism = IOFriendly.read[Double](file)
      val mercantilism = IOFriendly.read[Int](file)
      val stamina = IOFriendly.read[Double](file)
      val discipline = IOFriendly.read[Double](file)
      val rage = IOFriendly.read[Double](file)
      val chaos = IOFriendly.read[Double](file)
      val tax = IOFriendly.read[Double](file)
      val fortune = IOFriendly.read[Double](file)
      val force = IOFriendly.read[Double](file)
      val resilience = IOFriendly.read[Double](file)
      val evasion = IOFriendly.read[Double](file)
      IOFriendly.read[Int](file)
      IOFriendly.read[Int](file)
      IOFriendly.read[Int](file)
      val root = IOFriendly.read[Double](file)
      IOFriendly.read[Double](file)
      IOFriendly.read[Double](file)
      IOFriendly.read[String](file)
      IOFriendly.read[String](file)

      val faded = IOFriendly.read[Boolean](file)
      IOFriendly.read[Boolean](file)

      val karma = IOFriendly.read[Double](file)

      val maxEnergy = IOFriendly.read[Double](file)
      val energy = IOFriendly.read[Double](file)
      IOFriendly.read[Double](file)
      val maxHealth = IOFriendly.read[Double](file)
      val health = IOFriendly.read[Double](file)
      IOFriendly.read[Double](file)
      IOFriendly.read[Double](file)
      IOFriendly.read[Double](file)
      IOFriendly.read[Double](file)
      val upgr = IOFriendly.read[List[Int]](file)

      val apples = IOFriendly.read[Int](file)
      val oranges = IOFriendly.read[Int](file)
      val melons = IOFriendly.read[Int](file)
      IOFriendly.read[Int](file)
      IOFriendly.read[Int](file)

      val invSize = IOFriendly.read[Int](file)
      IOFriendly.read[Int](file)
      val invData = IOFriendly.read[List[Item]](file)
      IOFriendly.read[IOFriendly.IOEmptySeq](file)

      val playerSpace = IOFriendly.read[Int](file)
      IOFriendly.read[Int](file)
      IOFriendly.read[Int](file)

      val damageShift = IOFriendly.read[Double](file)
      IOFriendly.read[Double](file)

      val belt = IOFriendly.read[Belt](file)
      IOFriendly.read[String](file)
      IOFriendly.read[String](file)
      IOFriendly.read[String](file)
      IOFriendly.read[IOFriendly.IOEmptySeq](file)
      IOFriendly.read[IOFriendly.IOEmptySeq](file)
      IOFriendly.read[Int](file)

      GameData(
        era,
        atkMod,
        money,
        luck,
        strength,
        perseverence,
        vitality,
        metabolism,
        mercantilism,
        stamina,
        discipline,
        rage,
        chaos,
        tax,
        fortune,
        force,
        resilience,
        evasion,
        faded,
        karma,
        maxEnergy,
        energy,
        maxHealth,
        health,
        apples,
        oranges,
        melons,
        invSize,
        invData,
        playerSpace,
        damageShift,
        belt,
        upgr,
        root
      )
    }
  }

}
