import scala.io.Source
import java.time.{Duration, Instant}
import scala.collection.mutable.{ArrayBuffer, Map}

/** Advent of Code 2023 Day 7
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 7 puzzles.
 *
 * Created 7 Dec 2023
 * Link: https://adventofcode.com/2023/day/7
 */

class Day07 private(val title: String, val runType: Int):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run(): Unit = Day07.solution(runType)

    override def toString: String = s"Class ${Day07.puzzleTitle}"

end Day07

object Day07:

    val day = "07"
    val puzzleTitle = "Day 7: Camel Cards"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day07 = {
        new Day07("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day07 = {
        new Day07(title, runType)
    }

    private def solution(runType: Int): Unit = {

        println(s"--- Advent of Code 2023 ---")
        println(s"--- $puzzleTitle ---\n")

        // Read the puzzle input data file
        val filename = if (runType == 1) testData else realData
        print("Attempting to read input data file using ")
        if runType == 1 then
            println("TEST DATA ... ")
        else
            println("REAL INPUT DATA ...")

        // simple text file read:  Jan-Pieter van den Heuvel [Saving Christmas Using Scala](https://www.youtube.com/watch?v=tHU36gQ5iAI)
        val input = Source.fromResource(filename).getLines().toVector

        println("\nData Quality Control:")
        println(s"  Input file name: $filename")
        println(s"  Each line is a: ${input.head.getClass}")
        println(s"  Number lines: ${input.length}")
        println(s"  Number items per line: ${input.head.count(_ => true)}")
        println(s"  First line: ${input.head}")
        if input.size > 1 then
            println(s"  Last line: ${input.last}")
        println

        // ----------------------
        //  Common to both parts
        // ----------------------
        enum HandType:
            case HighCard, OnePair, TwoPair, Three, FullHouse, Four, Five

        val suits = "AKQJT98765432"

        // map relative card strengths to letters of the alphabet so
        // that when hands of same type are sorted they will be in order by card strength
        // ascending - lower ranks to higher for final ranked order list
        val suitsSortOrder = "ABCDEFGHIJKLM".reverse
        val suitsSortOrderMap = (suits zip suitsSortOrder).toMap

        val parseHandRE = raw"([\d\w]+) ([\d ]+)".r
        val FiveRE = s"([${suits}])\\1{4}".r.unanchored
        val FourRE = s"([${suits}])\\1{3}".r.unanchored

        case class Hand(cards: String, sorted: String, bid: Int)

        val listHands = input
            .map(y => y.trim.split(" +"))
            .map(x => Hand(x(0), x(0).sorted, x(1).toInt))
            .toList

        // listHands.foreach(println)

        def charCountMap(s: String): Map[Char, Int] = {
            // Ref: https://stackoverflow.com/questions/61631731/char-count-in-string

            s.foldLeft(Map.empty[Char, Int].withDefaultValue(0)) {
                (counts, s) => counts.clone.addOne(s, counts(s) + 1)
            }
        }

        def typeOfHand(h: Hand): HandType = {
            h.sorted match
                case FiveRE(c) => HandType.Five
                case FourRE(c) => HandType.Four
                case c => /* could be Full House, Three of a kind, or one pair or two pair or High Card */ {
                    val counts = charCountMap(c)
                    // counts.foreach(x => println(s"$c: $x"))
                    val pairs = counts.values.count(_ == 2)
                    val threes = counts.values.count(_ == 3)
                    if threes == 1 && pairs == 1 then
                        HandType.FullHouse
                    else if threes == 1 && pairs == 0 then
                        HandType.Three
                    else if pairs == 2 then
                        HandType.TwoPair
                    else if pairs == 1 then
                        HandType.OnePair
                    else
                        HandType.HighCard
                }
        }

        def typeOfHandJokersWild(h: Hand): HandType = {
            h.sorted match
                case FiveRE(c) => /* do nothing, same type */ HandType.Five
                case FourRE(c) => {
                    val charWith4 = charCountMap(h.cards).find(_._2 == 1).map(_._1)
                    charWith4 match
                        case Some(value) => {
                            if value == 'J' then
                                HandType.Five
                            else
                                HandType.Four
                        }
                        case None => ???  // shouldn't happen
                }
                case c => /* could be Full House, Three of a kind, or one pair or two pair or High Card */ {
                    val counts = charCountMap(c)
                    // counts.foreach(x => println(s"$c: $x"))
                    val pairs = counts.values.count(_ == 2)
                    val threes = counts.values.count(_ == 3)
                    if threes == 1 && pairs == 1 then
                        val charWith2 = charCountMap(h.cards).find(_._2 == 2).map(_._1)
                        charWith2 match
                            case Some(value) => {
                                if value == 'J' then
                                    HandType.Four
                                else
                                    HandType.FullHouse
                            }
                            case None => ???  // shouldn't happen
                    else if threes == 1 && pairs == 0 then {
                        val charWith3 = charCountMap(h.cards).find(_._2 == 3).map(_._1)
                        charWith3 match
                            case Some(value) => {
                                if value == 'J' then
                                    HandType.Three
                                else
                                    HandType.Four
                            }
                            case None => ???
                                HandType.Three
                    }
                    else if pairs == 2 then
                        val pairs = charCountMap(h.cards).filter(_._2 == 2).toMap
                        var isEitherPairAJoker = false
                        for e <-pairs do
                            if e._1 == 'J' then
                                isEitherPairAJoker = true

                        if isEitherPairAJoker then
                            HandType.TwoPair
                        else
                            HandType.Three
                    else if pairs == 1 then
                        val charWith2 = charCountMap(h.cards).find(_._2 == 2).map(_._1)
                        charWith2 match
                            case Some(value) => {
                                if value == 'J' then
                                    HandType.OnePair
                                else
                                    HandType.Three
                            }
                            case None => ???  // shouldn't happen
                    else
                        val charCounts = charCountMap(c)
                        if charCounts.contains('J') && charCounts('J') ==1  then
                            HandType.OnePair
                        else
                            HandType.HighCard
                }
        }

        // listHands.foreach(x => println(s"Hand: ${x.cards}, Type: ${typeOfHand(x)}"))

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        /*
            the first step is to put the hands in order of strength:
          Rank: rule 1) by type of hand,
                rule 2) relative strength of cards

            Note: https://stackoverflow.com/questions/40104898/scala-enumeration-compare-order
         */
        // sort by enum, lowest rank to have lowest index
        // val sortedByType = listHands.sortWith(typeOfHand(_).ordinal < typeOfHand(_).ordinal)
        // sortedByType.foreach(x => println(s"${typeOfHand(x)}, $x"))

        val sortedByType = scala.collection.mutable.Map[HandType, ArrayBuffer[Hand]]()
        for hand <- listHands do
            val typ = typeOfHand(hand)
            if sortedByType.contains(typ) then
                sortedByType(typ) += hand
            else
                sortedByType += (typ -> ArrayBuffer[Hand](hand))
        // println(s"${hand.cards} ${hand.bid} $typ")

        val rankedHands = ArrayBuffer[Hand]()
        for key <- HandType.values do
            if sortedByType.contains(key) then
                if sortedByType(key).length == 1 then
                    rankedHands += sortedByType(key)(0)
                else
                    /* compare cards */
                    val byStrength = scala.collection.mutable.Map[String, Hand]()
                    for h <- sortedByType(key) do
                        var score = ""
                        for i <- 0 until h.cards.length do
                            score += suitsSortOrderMap(h.cards(i))
                        if byStrength.contains(score) then
                            println("Error")
                        else
                            byStrength += (score -> h)

                    val ks = byStrength.keys.toList.sorted
                    for k <- ks do
                        rankedHands += byStrength(k)

        /* should have list with index from 0 to N of each hand in order by hand type and card strength */
        /*
             Each hand wins an amount equal to its bid multiplied by its rank,
             where the weakest hand gets rank 1
          */
        var winnings = BigInt(0)
        for h <- rankedHands.zipWithIndex do
            // println(s"${h._1.bid} * ${h._2 + 1}")
            winnings += h._1.bid * (h._2 + 1)

        // 249727050 too low
        // 251820259 too high
        // 251819223 too high
        // 251806792

        val answerP1 = winnings
        println(s"Part 1: Find the rank of every hand in your set. What are the total winnings? A: $answerP1")
        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Part 1 run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()

        val suitsP2 = "AKQT98765432J"
        val suitsSortOrderP2 = "ABCDEFGHIJKLM".reverse
        val suitsSortOrderMapP2 = (suits zip suitsSortOrder).toMap

        val sortedByTypeP2 = scala.collection.mutable.Map[HandType, ArrayBuffer[Hand]]()
        for hand <- listHands do
            val typ = typeOfHand(hand)
            if sortedByType.contains(typ) then
                sortedByType(typ) += hand
            else
                sortedByType += (typ -> ArrayBuffer[Hand](hand))
        // println(s"${hand.cards} ${hand.bid} $typ")

        val answerP2 = 0
        println(s"Part 2: Using the new joker rule, find the rank of ")
        println(s"every hand in your set. What are the new total winnings?  A: $answerP2")
        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Part 2 run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day07
