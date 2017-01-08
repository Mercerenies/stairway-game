
package com.mercerenies.stairway.game.world

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.stat.ImprovableStats
import com.mercerenies.stairway.game.belt._
import com.mercerenies.stairway.space._
import com.mercerenies.stairway.enemy._
import com.mercerenies.stairway.product.item._
import com.mercerenies.stairway.product.Scroll
import com.mercerenies.stairway.util
import com.mercerenies.stairway.luck.{DiceValue, DiceNumbers}
import scala.util.Random
import scala.collection.immutable.Stream

class BeltSystem(
  master: StandardGame.Master,
  seed: Option[Long] = None) {
  import util.RandomImplicits._

  private class SpaceGenerator(
    feed: GeneratorFeed,
    val space: Space,
    override val minTimer: Int,
    override val maxTimer: Int)
      extends AbstractGenerator(feed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {
    override def nextSpace() = space
  }

  private object RedGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with LeadInGenerator[GeneratorFeed] {

    private var doubleCounter = 0
    private var tripleCounter = 0
    private var quadrupleCounter = 0

    def doubleFreq = master.era match {
      case _ => 50
    }
    def tripleFreq = master.era match {
      case _ => 100
    }
    def quadrupleFreq = master.era match {
      case _ => 100
    }

    override def minTimer = master.era match {
      case _ => 10
    }
    override def maxTimer = master.era match {
      case _ => 15
    }

    override def leadIn() = master.era match {
      case _ => 0
    }

    override def nextSpace() = {
      doubleCounter += 1
      if (doubleCounter >= doubleFreq) {
        doubleCounter = 0
        tripleCounter += 1
        if (tripleCounter >= tripleFreq) {
          tripleCounter = 0
          quadrupleCounter += 1
          if (quadrupleCounter >= quadrupleFreq) {
            quadrupleCounter = 0
            RedSpace(RedSpace.Quadruple)
          } else {
            RedSpace(RedSpace.Triple)
          }
        } else {
          RedSpace(RedSpace.Double)
        }
      } else {
        RedSpace(RedSpace.Single)
      }
    }

  }

  private object EnemySeqGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with EnemySequenceGenerator[GeneratorFeed] {

    private val eraSettings: Array[Seq[(Enemy.Entropy => Enemy, Seq[Int])]] = Array(
      List(entry(new Rat(master, _), 3, 3, 4))
    )

    private def entry(func: Enemy.Entropy => Enemy, poss: Int*): (Enemy.Entropy => Enemy, Seq[Int]) =
      (func, poss)

    private def settings(era: Int) = eraSettings(era - 1)

    override def minTimer = master.era match {
      case _ => 20
    }

    override def maxTimer = master.era match {
      case _ => 25
    }

    override def ready: Boolean = eraSettings.isDefinedAt(master.era - 1) && super.ready

    override def nextEnemy() = random.nextOf(settings(master.era)) match {
      case (enemy, paces) => (enemy(Enemy.entropy(random)), random.nextOf(paces))
    }

  }

  private object FruitGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case _ => 9
    }
    override def maxTimer = master.era match {
      case _ => 18
    }

    override def nextSpace(): Space = FruitSpace

  }

  private object RecoveryGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {

    val cycle = util.cycle(IncomeSpace, HealthSpace).iterator

    override def minTimer = master.era match {
      case _ => 7
    }
    override def maxTimer = master.era match {
      case _ => 10
    }

    override def nextSpace(): Space = cycle.next()

  }

  private object TaxGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with LeadInGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case _ => 10
    }
    override def maxTimer = master.era match {
      case _ => 25
    }

    override def leadIn() = master.era match {
      case _ => 0
    }

    override def nextSpace(): Space = TaxSpace

  }

  ///// Lottery and dojo

  private object ItemGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case _ => 20
    }
    override def maxTimer = master.era match {
      case _ => 25
    }

    def itemSampler: Seq[Item] = master.era match {
      case _ => List(Coffee, Coffee, Sundae)
    }

    def nextItem(): Item = random.nextOf(itemSampler)

    override def nextSpace(): Space = ItemSpace(nextItem(), nextItem(), nextItem())

  }

  private object MysteryGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case _ => 13
    }
    override def maxTimer = master.era match {
      case _ => 18
    }

    def boxSampler: Seq[Int] = master.era match {
      case _ => List(3)
    }

    def nextBoxes(): Int = random.nextOf(boxSampler)

    override def nextSpace(): Space = MysterySpace(nextBoxes())

  }

  private object LottoGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {
    import Ordering.Implicits._

    override def minTimer = master.era match {
      case _ => 25
    }
    override def maxTimer = master.era match {
      case _ => 28
    }

    def lottoSampler: Seq[(Int, DiceValue)] = master.era match {
      case _ => List((3, DiceValue(9)), (3, DiceValue(10)), (3, DiceValue(11)), (3, DiceValue(11)))
    }

    def houseRules(x: (Int, DiceValue)): Int = x match {
      case (n, v) => (1 / DiceNumbers(n).satisfy(_.sum >= v)).floor.toInt
    }

    def nextLotto(): LotterySpace = random.nextOf(lottoSampler) match {
      case (n, v) => LotterySpace(n, v, houseRules((n, v)))
    }

    override def nextSpace(): Space = nextLotto()

  }

  private object IceGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case _ => 28
    }
    override def maxTimer = master.era match {
      case _ => 44
    }

    override def nextSpace(): Space = IceSpace

  }

  private object DojoGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {

    val allUpgrades = random.shuffle(master.stats.levels.standardUpgrades)
    val upgradeIter = util.cycle(allUpgrades: _*).iterator

    override def minTimer = master.era match {
      case _ => 46
    }
    override def maxTimer = master.era match {
      case _ => 54
    }

    private def cyclicLevels(): Seq[ImprovableStats.UpgradeSlot[Any]] =
      List(upgradeIter.next(), upgradeIter.next())

    private def randomLevels(non: Seq[ImprovableStats.UpgradeSlot[Any]]): Seq[ImprovableStats.UpgradeSlot[Any]] = {
      val upgrades = allUpgrades.filterNot(non.contains(_))
      val fst = random.nextOf(upgrades)
      val snd = random.nextOf(upgrades.filterNot(_ == fst))
      List(fst, snd)
    }

    private def levelsSet(): Seq[ImprovableStats.UpgradeSlot[Any]] = {
      val cyclic = cyclicLevels()
      val rand = randomLevels(cyclic)
      cyclic ++ rand
    }

    override def nextSpace() = DojoSpace(random.shuffle(levelsSet()): _*)

  }

  private object ScrollGenerator ///// Properly set this up with real scrolls
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {

    val scroll1 = new Scroll(Scroll.Effect("ATK +1", m => m.stats.money += 1), Scroll.Effect("Test2", m => m.stats.money += 2))
    val scroll2 = new Scroll(Scroll.Effect("HP +50", m => m.stats.money += 1), Scroll.Effect("And2", m => m.stats.money += 2))
    val scroll3 = new Scroll(Scroll.Effect("Item Slot", m => m.stats.money += 1), Scroll.Effect("Ad2", m => m.stats.money += 2))

    override def minTimer = master.era match {
      case _ => 3
    }
    override def maxTimer = master.era match {
      case _ => 5
    }

    override def ready = false

    private var age = 0

    override def nextSpace() = {
      age += 1
      ScrollSpace(age, scroll1, scroll2, scroll3)
    }

  }

  private object BaseFeed extends GeneratorFeed(master) {

    override lazy val generators: Seq[Generator[GeneratorFeed]] =
      List(
        RecoveryGenerator, // Recovery
        MysteryGenerator, LottoGenerator,// Gambling
        FruitGenerator, ItemGenerator, // Shopping
        DojoGenerator, ScrollGenerator, // Training
        TaxGenerator, RedGenerator, IceGenerator, // Negative
        EnemySeqGenerator // Fighting
      )

    override def default(index: util.Index): Space =
      if (index < util.Index.Absolute(0)) EmptySpace else NeutralSpace

  }

  private object BossFeedSystem extends BossSystem[BaseFeed.type](master, BaseFeed) {

    override lazy val bosses: Seq[BossEnemy] = List(new Ratticus(master))

    lazy val stoppingPoints: Seq[Int] = List(30)

    private var lastSwitch: util.Index = util.Index.Absolute(0)

    override def timeToSwitch(index: util.Index): Boolean =
      stoppingPoints andThen (index >= lastSwitch + _ - 1) applyOrElse (master.era - 1, { (_: Int) => false })

    override def onSwitch(arg: AlternatingFeed.Alternate): Unit = {
      super.onSwitch(arg)
      // If we're switching back to the regular feed, set the basis point
      if (arg == regularFeed)
        lastSwitch = util.Index.Absolute(AltFeed.topDefined)
    }

  }

  def this(master: StandardGame.Master, seed: Long) = this(master, Some(seed))

  lazy val random: Random = seed match {
    case None => new Random()
    case Some(x) => new Random(x)
  }

  val belt: OverrideConveyer[ConveyerFeed] =
    new OverrideConveyer(master, BossFeedSystem.AltFeed)

  def eraChanged(newEra: Int): Unit = {
    BaseFeed.eraChanged(newEra)
  }

}
