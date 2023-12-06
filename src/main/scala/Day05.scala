import scala.io.Source
import java.time.{Duration, Instant}

/** Advent of Code 2023 Day 5
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 5 puzzles.
 *
 * Created 5 Dec 2023
 * Link: https://adventofcode.com/2023/day/5
 */

class Day05 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day05.runPuzzle(runType)

    override def toString: String = s"Class ${Day05.puzzleTitle}"

end Day05

object Day05:

    val day = "05"
    val puzzleTitle = "Day 5: If You Give A Seed A Fertilizer"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day05 = {
        new Day05("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day05 = {
        new Day05(title, runType)
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

        // read input file - create big string of all lines, preserve new lines
        val input = Source.fromResource(filename).getLines().mkString("\n")

        // println(input)

        println("\nData Quality Control:")
        println(s"  Input file name: $filename")
        println(s"  Each line is a: ${input.getClass}")
        println(s"  Number lines: ${input.filter(x => x == '\n').length + 1}")
        println(s"  First line: ${input.head}")
        if input.size > 1 then
            println(s"  Last line: ${input.last}")
        println

        // ----------------------
        //  Common to both parts
        // ----------------------

        val seedsRE = raw"(seeds: +)([\d ]*\n)".r
        val soilMapRE = raw"(?s)((seed-to-soil map:\n)(.*?)(?:(?:\r*\n){2}))".r

        def buildSeedsList(input: String): List[Int] = {
            seedsRE.findAllIn(input).matchData.map(_.group(2)).mkString.split(" +").map(_.trim.toInt).toList
            // println(seedsRE.findAllIn(input).matchData.map(x => x.group(2)).mkString)
        }

        def buildSeedToSoilMap(input: String): Map[Int,Int] = {
            val t = soilMapRE.findAllIn(input).matchData.map(_.group(3)).toList
            // todo: parse out list of strings, line by line, expanding each.

            println(t)
            Map[Int,Int](0 -> 0)
        }

        val seeds = buildSeedsList(input)
        //seeds.foreach(println)
        val seedsToSoil = buildSeedToSoilMap(input)
        seedsToSoil.foreach(println)

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        val answerP1 = 0

        println(s"Part 1: What is the lowest location number that corresponds to any of the initial seed numbers?  A: $answerP1")
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

end Day05
