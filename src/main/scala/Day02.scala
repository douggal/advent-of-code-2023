import java.time.{Duration, Instant}
import scala.io.Source
import scala.collection.mutable.ArrayBuffer

/** Advent of Code 2023 Day 02
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 2 puzzles.
 *
 * Created 2 Dec 2023
 * Link: https://adventofcode.com/2023/day/2
 */

class Day02 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day02.runPuzzle(runType)

    override def toString: String = s"Class ${Day02.puzzleTitle}"

end Day02

object Day02:

    val day = "02"
    val puzzleTitle = "Day 02: ???"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day02 = {
        new Day02("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day02 = {
        new Day02(title, runType)
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

        // ----------------------
        //  Common to both parts
        // ----------------------
        val gameHeaderRE = raw"(Game [1-9]+: )(.+)".r

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        // Use parallel arrays,  one each for red, green and blue
        // ignore index 0
        // each index represents game #, and its Vector of Ints is counts from each draw
        val reds = ArrayBuffer[ArrayBuffer[Int]]()
        val greens = ArrayBuffer[ArrayBuffer[Int]]()
        val blues = ArrayBuffer[ArrayBuffer[Int]]()

        val colors = List("red", "green", "blue")

        for li <- input do
            // peel off the game number
            val game = gameHeaderRE.findAllIn(li).toVector.tail.head

            // split on ;
            // Ex:  3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            val draws = game.split(";")

            // split on ,
            // Ex 3 blue, 4 red
            for draw <- draws do
                reds += ArrayBuffer[Int]()
                greens += ArrayBuffer[Int]()
                blues += ArrayBuffer[Int]()
                val xs = draw.split(",")
                for x <- xs do
                    for color <- colors do
                        color match
                            case "red" => reds.last += x.split(" ").head.trim.toInt
                            case "green" => greens.last += x.split(" ").head.trim.toInt
                            case "blue" => blues.last += x.split(" ").head.trim.toInt
                            case _ => { println("Error"); System.exit(1) }




        println(s"Part 1: Determine which games would have been possible if the bag had been loaded with only")
        println(s"12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?")

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

end Day02
