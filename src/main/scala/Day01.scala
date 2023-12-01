import scala.io.Source
import java.time.{Duration, Instant}

/** Advent of Code 2023 Day XX
 *
 * Defines a class, its companion object and a runner method for
 * the Advent of Code Day 1 puzzles.
 *
 * Created 1 Dec 2023
 * Link: https://adventofcode.com/2023/day/1
 */

class Day01 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day01.runPuzzle(runType)

    override def toString: String = s"Class ${Day01.puzzleTitle}"

end Day01

object Day01:

    val day = "01"
    val puzzleTitle = "--- Day 1: Trebuchet?! ---"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day01 = {
        new Day01("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day01 = {
        new Day01(title, runType)
    }

    private def runPuzzle(runType: Int): Unit = {

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

        // Common to both parts
        // code goes here ...

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        // a place to hold all the numbers
        val a = scala.collection.mutable.ArrayBuffer[Int]()

        // regex match digits
        val digitsRE = raw"[0-9]".r

        for li <- input do
            // filter each line of input out and return only the digits,
            // then take first and last digits
            // if only one digit, then take first digit twice.
            val matches = digitsRE.findAllIn(li).toVector
            val h = matches.head
            val t = if matches.length == 1 then matches.head else matches.tail.last
            a += h.toInt*10 + t.toInt

        println(s"Part 1:  What is the sum of all of the calibration values?")
        println(s"${a.sum}")

        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Part 1 run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        println(s"Part 2: TBD ???")
        val p2T0 = Instant.now()



        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Part 2 run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day01
