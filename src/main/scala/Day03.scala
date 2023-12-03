import scala.io.Source
import java.time.{Duration, Instant}
import scala.collection.mutable.{ArrayBuffer}

/** Advent of Code 2023 Day 3
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 3 puzzles.
 *
 * Created 3 Dec 2023
 * Link: https://adventofcode.com/2023/day/3
 */

class Day03 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day03.runPuzzle(runType)

    override def toString: String = s"Class ${Day03.puzzleTitle}"

end Day03

object Day03:

    val day = "03"
    val puzzleTitle = "--- Day 3: Gear Ratios ---"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day03 = {
        new Day03("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day03 = {
        new Day03(title, runType)
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
        /*
           Create a grid from the input data:  1-D array of Char
           Row-oriented, y-down
           Ref:  (Amit's grid parts)[https://www.redblobgames.com/grids/parts/]
            (Chutes and Ladders)[https://docs.swift.org/swift-book/documentation/the-swift-programming-language/controlflow/]

            Index         0   1   2   3   4   5   6   7   8   9   10   11
            Data          4   6   7   .   .   1   1   4   .   .   .    .
            rows (y)      0   0   0   0   0   0   0   0   0   0   1    1
            columns (x)   0   1   2   3   4   5   6   7   8   9   0    1
        */
        val grid = ArrayBuffer[Char]()
        val maxRow = input.length
        val maxCol = input.head.count(_ => true)
        for li <- input do
            for c <- li.toCharArray do
                grid += c

        val i = 0

        def coordsToIndex(r: Int, c: Int): Int = {
            // convert row and column to index in the grid


            ???
        }

        def getNeighbors(r: Int, c: Int): ArrayBuffer[Char] = {
            // returns a Vector of the values of this cell's neighbors
            // neighbors share an edge with the cell a row r and column c
            // or share a corner touch - the diagonals

            val moves = List((-1,0),(-1,1),(0,1),(1,1),(1,0),(1,-1),(0,-1),(-1,-1))  // clockwise from top
            val result = ArrayBuffer[Char]()
            for t <- moves do
                if r+t._1 >= 0 && r+t._1 < maxRow then
                    if c+t._2 >= 0 && c+t._2 < maxCol then
                        result += grid(r+t._1,c+t._2)
            result
        }


        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()





        println(s"Part 1: What is the sum of all of the part numbers in the engine schematic?")

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

end Day03
