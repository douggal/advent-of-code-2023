import scala.io.Source
import java.time.{Duration, Instant}

/** Advent of Code 2023 Day 15
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 15 puzzles.
 *
 * Created 22 Dec 2023
 * Link: https://adventofcode.com/2023/day/15
 */

class Day15 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day15.solution(runType)

    override def toString: String = s"Class ${Day15.puzzleTitle}"

end Day15

object Day15:

    val day = "15"
    val puzzleTitle = "Day 15: Lens Library"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day15 = {
        new Day15("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day15 = {
        new Day15(title, runType)
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
        def hash(cs: Array[Char]): Int = {

            // a Char in Scala is a 16-bit unsigned integer

            var h = 0
            for c <-cs do
                h += c.charValue()
                h *= 17
                h = h % 256

            h
        }

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        // initialization sequence
        val is = input.head.split(",").map(x => x.toCharArray).toList
        val hashs = is.map(x => hash(x)).toVector

        val answerP1 = hashs.sum
        println(s"Part 1: Run the HASH algorithm on each step in the initialization sequence.")
        println(s"What is the sum of the results?   A: $answerP1")

        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()



        val answerP2 = 0
        println(s"Part 2: TBD ???  A: $answerP2")

        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day15
