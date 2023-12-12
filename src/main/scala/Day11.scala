import scala.io.Source
import java.time.{Duration, Instant}
import scala.collection.mutable.{ArrayBuffer, Set}

/** Advent of Code 2023 Day 11
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 11 puzzles.
 *
 * Created 11 Dec 2023
 * Link: https://adventofcode.com/2023/day/11
 */

class Day11 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day11.solution(runType)

    override def toString: String = s"Class ${Day11.puzzleTitle}"

end Day11

object Day11:

    val day = "11"
    val puzzleTitle = "Day 11: Cosmic Expansion"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day11 = {
        new Day11("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day11 = {
        new Day11(title, runType)
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

        val input = Source.fromResource(filename).getLines().map(_.toCharArray).toVector

        println("\nData Quality Control:")
        println(s"  Input file name: $filename")
        println(s"  Each line is a: ${input.head.getClass}")
        println(s"  Number lines: ${input.length}")
        println(s"  Number items per line: ${input.head.count(_ => true)}")
        println(s"  First line: ${input.head.mkString(" ")}")
        if input.size > 1 then
            println(s"  Last line: ${input.last.mkString(" ")}")
        println

        // ----------------------
        //  Common to both parts
        // ----------------------
        // code goes here ...

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        // which rows need to be expanded
        val rowsToExpand = input
            .zipWithIndex
            .filter(x => x._1.count(_ != '.') == 0)
            .map(x => x._2)

        // which columns to expand
        val colsToExpand = ArrayBuffer[Int]()
        for i <- input(0).indices do // columns
            val slice = ArrayBuffer[Char]()
            for j <- input.indices do // iterate down the rows
                slice += input(j)(i)
            end for
            if slice.count(_ != '.') == 0 then
                colsToExpand += i
            end if

        case class Point(r: Int, c: Int)
        case class Galaxy(N: Int, coord: Point)

        // Expand the universe
        // each new column adds one to column number in original universe
        val newCols = colsToExpand.zipWithIndex.map(x => x._1+x._2)
        val newRows = rowsToExpand.zipWithIndex.map(x => x._1+x._2)

        // The universe is a list of galaxies
        val universe = ArrayBuffer[Galaxy]()
        var i = 1
        for r <- input.indices do
            for c <- input(0).indices do
                if input(r)(c) == '#' then
                    universe += Galaxy(i,Point(r,c))
                    i += 1
                end if
            end for
        end for

        // universe.foreach(println)

        // expand the universe:  rows
        val universe2 = ArrayBuffer[Galaxy]()
        for g <- universe do
            val xtransform = rowsToExpand.takeWhile(_ < g.coord.r).length
            universe2 += Galaxy(g.N, Point(g.coord.r + xtransform, g.coord.c))
        end for


        // expand the universe by columns
        val universe3 = ArrayBuffer[Galaxy]()
        for g <- universe2 do
            val xtransform = colsToExpand.takeWhile(_ < g.coord.c).length
            universe3 += Galaxy(g.N, Point(g.coord.r, g.coord.c + xtransform))
        end for


        // Find the Manhattan distance between each pair of galaxies
        case class Distance(galaxies: Set[Int], dist: Int)
        val dists = ArrayBuffer[Distance]()
        for i <- universe3.indices do
            for j <- i+1 until universe3.length do
                // taxi cab or manhattan distance
                val d = Math.abs(universe3(i).coord.c - universe3(j).coord.c) + Math.abs(universe3(i).coord.r - universe3(j).coord.r)
                dists += Distance(Set(universe3(i).N,universe3(j).N), d)

        dists.foreach(println)

        val answerP1 = dists.map(_.dist).sum
        println(s"Part 1: Expand the universe, then find the length of the shortest path between every pair of galaxies. ")
        println(s"What is the sum of these lengths? A: $answerP1")

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

end Day11
