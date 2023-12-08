import scala.io.Source
import java.time.{Duration, Instant}

/** Advent of Code 2023 Day 8
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 8 puzzles.
 *
 * Created 8 Dec 2023
 * Link: https://adventofcode.com/2023/day/8
 */

class Day08 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day08.solution(runType)

    override def toString: String = s"Class ${Day08.puzzleTitle}"

end Day08

object Day08:

    val day = "08"
    val puzzleTitle = "--- Day 8: Haunted Wasteland ---"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day08 = {
        new Day08("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day08 = {
        new Day08(title, runType)
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
        // code goes here ...

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()


        val answerP1 = 0
        println(s"Part 1: Starting at AAA, follow the left/right instructions. ")
        println(s"How many steps are required to reach ZZZ?  A: $answerP1")
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

end Day08
