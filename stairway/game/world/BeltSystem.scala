
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
  import BeltSystem._

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
      case 1 => 50
      case 2 => 40
      case _ => NoGenerate
    }
    def tripleFreq = master.era match {
      case 1 => 100
      case 2 => 100
      case _ => NoGenerate
    }
    def quadrupleFreq = master.era match {
      case 1 => 100
      case 2 => 100
      case _ => NoGenerate
    }

    override def minTimer = master.era match {
      case 1 => 10
      case 2 => 10
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => 15
      case 2 => 14
      case _ => NoGenerate
    }

    override def leadIn() = master.era match {
      case 1 => 0
      case 2 => 0
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

    type EnemySpawnArg = (() => Enemy, Seq[Int])

    private def basicRat() = new Rat(master, Enemy.entropy(random))
    private def ratTeam() = new EnemyTeam(master, basicRat(), basicRat())
    private def basicPear() = new Pear(master, Enemy.entropy(random))

    private val eraSettings: Array[Seq[EnemySpawnArg]] = Array(
      List(
        entry(basicRat(), 3, 3, 4)
      ),
      List(
        entry(ratTeam(), 3, 4, 5),
        entry(ratTeam(), 3, 4, 5),
        entry(basicPear(), 4, 5, 5),
        entry(basicPear(), 4, 5, 5),
        entry(basicRat(), 3, 3, 4)
      )
    )

    private def entry(func: => Enemy, poss: Int*): EnemySpawnArg =
      (() => func, poss)

    private def settings(era: Int) = eraSettings(era - 1)

    override def minTimer = master.era match {
      case 1 => 20
      case 2 => 16
      case _ => NoGenerate
    }

    override def maxTimer = master.era match {
      case 1 => 25
      case 2 => 21
      case _ => NoGenerate
    }

    override def ready: Boolean = eraSettings.isDefinedAt(master.era - 1) && super.ready

    override def nextEnemy() = random.nextOf(settings(master.era): _*) match {
      case (enemy, paces) => (enemy(), random.nextOf(paces: _*))
    }

  }

  private object FruitGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case 1 => 9
      case 2 => 10
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => 18
      case 2 => 20
      case _ => NoGenerate
    }

    override def nextSpace(): Space = FruitSpace

  }

  private object RecoveryGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {

    val cycle = util.cycle(IncomeSpace, HealthSpace).iterator

    override def minTimer = master.era match {
      case 1 => 7
      case 2 => 7
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => 10
      case 2 => 10
      case _ => NoGenerate
    }

    override def nextSpace(): Space = cycle.next()

  }

  private object TaxGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with LeadInGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case 1 => 10
      case 2 => 10
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => 25
      case 2 => 25
      case _ => NoGenerate
    }

    override def leadIn() = master.era match {
      case 1 => 0
      case 2 => 0
      case _ => 0
    }

    override def nextSpace(): Space = TaxSpace

  }

  private object ItemGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case 1 => NoGenerate
      case 2 => 20
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => NoGenerate
      case 2 => 25
      case _ => NoGenerate
    }

    def itemSampler: Seq[Item] = master.era match {
      case 1 => List()
      case 2 => List(Coffee, Sundae, ThrowingKnife)
      case _ => List()
    }

    override def ready = !itemSampler.isEmpty && super.ready

    def nextItem(): Item = random.nextOf(itemSampler: _*)

    override def nextSpace(): Space = ItemSpace(nextItem(), nextItem(), nextItem())

  }

  private object MysteryGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case 1 => 13
      case 2 => 13
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => 18
      case 2 => 22
      case _ => NoGenerate
    }

    def boxSampler: Seq[Int] = master.era match {
      case 1 => List(3)
      case 2 => List(3, 4, 4)
      case _ => List(1)
    }

    def nextBoxes(): Int = random.nextOf(boxSampler: _*)

    override def nextSpace(): Space = MysterySpace(nextBoxes())

  }

  private object LottoGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {
    import Ordering.Implicits._

    override def minTimer = master.era match {
      case 1 => 25
      case 2 => 20
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => 28
      case 2 => 27
      case _ => NoGenerate
    }

    def lottoSampler: Seq[(Int, DiceValue)] = master.era match {
      case 1 => List((3, DiceValue(9)), (3, DiceValue(10)), (3, DiceValue(11)), (3, DiceValue(11)))
      case 2 => List((3, DiceValue(10)), (3, DiceValue(10)), (3, DiceValue(11)), (3, DiceValue(12)))
      case _ => List((1, DiceValue(999)))
    }

    def houseRules(x: (Int, DiceValue)): Int = x match {
      case (n, v) => math.max((1 / DiceNumbers(n).satisfy(_.sum >= v)).floor.toInt, 2)
    }

    def nextLotto(): LotterySpace = random.nextOf(lottoSampler: _*) match {
      case (n, v) => LotterySpace(n, v, houseRules((n, v)))
    }

    override def nextSpace(): Space = nextLotto()

  }

  private object IceGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case 1 => 28
      case 2 => 26
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => 44
      case 2 => 44
      case _ => NoGenerate
    }

    override def nextSpace(): Space = IceSpace

  }

  private object DojoGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {

    val allUpgrades: Seq[ImprovableStats.UpgradeSlot[_]] = random.shuffle(master.stats.levels.standardUpgrades)
    val upgradeIter: Iterator[ImprovableStats.UpgradeSlot[_]] = util.cycle(allUpgrades: _*).iterator

    override def minTimer = master.era match {
      case 1 => NoGenerate
      case 2 => 20
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => NoGenerate
      case 2 => 25
      case _ => NoGenerate
    }

    private def cyclicLevels(): Seq[ImprovableStats.UpgradeSlot[_]] =
      List(upgradeIter.next(), upgradeIter.next())

    private def randomLevels(non: Seq[ImprovableStats.UpgradeSlot[_]]): Seq[ImprovableStats.UpgradeSlot[_]] = {
      val upgrades = allUpgrades.filterNot(non.contains(_))
      val fst = random.nextOf(upgrades: _*)
      val snd = random.nextOf(upgrades.filterNot(_ == fst): _*)
      List(fst, snd)
    }

    private def levelsSet(): Seq[ImprovableStats.UpgradeSlot[_]] = {
      val cyclic = cyclicLevels()
      val rand = randomLevels(cyclic)
      cyclic ++ rand
    }

    override def nextSpace() = DojoSpace(random.shuffle(levelsSet()): _*)

  }

  private object FruitTheftGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with ClusterGenerator[GeneratorFeed]
      with LeadInGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case 1 => NoGenerate
      case 2 => 12
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => NoGenerate
      case 2 => 24
      case _ => NoGenerate
    }

    override def leadIn() = master.era match {
      case 1 => 0
      case 2 => 0
      case _ => 0
    }

    override def clusterSize() = master.era match {
      case 1 => 1
      case 2 => 1
      case _ => 1
    }

    override def nextSpace(): Space = FruitTheftSpace

  }

  private object ScrollGeneratorA
      extends AbstractGenerator(BaseFeed, random)
      with FixedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with CounterGenerator[GeneratorFeed] {

    val scrolls = Array(
       new Scroll(
         Scroll.LevelEffect("HP +10", _.health.buffBy(10)),
         Scroll.LevelEffect("HP +20", _.health.buffBy(20)),
         Scroll.LevelEffect("HP +50", _.health.buffBy(50))
       ),
      new Scroll(
        Scroll.LevelEffect("Energy +10", _.energy.buffBy(10)),
        Scroll.LevelEffect("Energy +20", _.energy.buffBy(20)),
        Scroll.LevelEffect("Energy +50", _.energy.buffBy(50))
      ),
      new Scroll(
        Scroll.LevelEffect("Luck +10%", _.luck.buffBy(10)),
        Scroll.LevelEffect("Luck +20%", _.luck.buffBy(20)),
        Scroll.LevelEffect("Luck +50%", _.luck.buffBy(50))
      )
    )

    override def fixedTimer = master.era match {
      case 1 => NoGenerate
      case 2 => if (counter <= 0) 20 else NoGenerate
      case _ => NoGenerate
    }

    override def nextSpace() =
      ScrollSpace(counter, scrolls: _*)

  }

  private object BaseFeed extends GeneratorFeed(master) {

    override lazy val generators: Seq[Generator[GeneratorFeed]] =
      List(
        ScrollGeneratorA, // Scrolls
        RecoveryGenerator, // Recovery
        MysteryGenerator, LottoGenerator,// Gambling
        FruitGenerator, ItemGenerator, // Shopping
        DojoGenerator, // Training
        TaxGenerator, RedGenerator, FruitTheftGenerator, IceGenerator, // Negative
        EnemySeqGenerator // Fighting
      )

    override def default(index: util.Index): Space =
      if (index < util.Index.Absolute(0)) EmptySpace else NeutralSpace

  }

  private object BossFeedSystem extends BossSystem[BaseFeed.type](master, BaseFeed) {

    override lazy val bosses: Seq[BossEnemy] = List(new Ratticus(master), new ShakesPear(master))

    lazy val stoppingPoints: Seq[Int] = List(50, 80)

    private var lastSwitch: util.Index = util.Index.Absolute(0)

    override def timeToSwitch(index: util.Index): Boolean =
      stoppingPoints andThen (index >= lastSwitch + _ - 1) applyOrElse (master.era - 1, { (_: Int) => false })

    def tillNextBoss: util.Index.Type = {
      val index = master.player.occupiedPosition
      stoppingPoints andThen
        (lastSwitch + _ - index) andThen
        (math.max(_, 0)) applyOrElse
        (master.era - 1, { (_: Int) => 0 })
    }

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

  def tillNextBoss: util.Index.Type = BossFeedSystem.tillNextBoss

}

object BeltSystem {

  val NoGenerate = Int.MaxValue

}
