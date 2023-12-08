import scala.io.Source
import java.time.{Duration, Instant}

/** Advent of Code 2023 Day 7
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 7 puzzles.
 *
 * Created 7 Dec 2023
 * Link: https://adventofcode.com/2023/day/7
 */

class Day07 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day07.solution(runType)

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
        enum Color:
            case HighCard, OnePair, TwoPair, Three, FullHouse, Four, Five

        case class Hand(cards: String, bid: Int)

        val parseHandRE = raw"([\d\w]+) ([\d ]+)".r

        val listHands = input
            .map(y => y.trim.split(" +"))
            .map(x => Hand(x(0), x(1).toInt))
            .toList

        listHands.foreach(println)

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        println(s"Part 1: TBD ???")

        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Part 1 run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()

        println(s"Part 2: TBD ???")

        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Part 2 run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day07