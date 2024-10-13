import fansi.*

import java.time.{Duration, Instant}
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, Map}
import scala.io.Source

/** Advent of Code 2023 Day 25
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 25 puzzles.
 *
 * Created 25 Dec 2023
 * Link: https://adventofcode.com/2023/day/25
 *
 * 2nd try 31 May 2024
 * 3rd try 12 Oct 2024 - refactor Day25.scala based on my Python + NetworkX solution
 */

class Day25_v2 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day25_v2.solution(runType)

    override def toString: String = s"Class ${Day25_v2.puzzleTitle}"

end Day25_v2

object Day25_v2:

    val day = "25"
    val puzzleTitle = "Day 25: Snowverload"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day25_v2 = {
        new Day25_v2("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day25_v2 = {
        new Day25_v2(title, runType)
    }

    private def solution(runType: Int): Unit = {

        println(s"--- Advent of Code 2023 ---")
        println(s"--- $puzzleTitle ---\n")

        // Read the puzzle input data file
        val filename = if (runType == 1) testData else realData
        // val filename = "25-test-2.txt"

        print("Attempting to read input data file using ")
        if runType == 1 then
            println("TEST DATA ... ")
        else
            println("REAL INPUT DATA ...")

        // the working directory is the project root folder
        // place data files in subfolder ./data
        val wd = System.getProperty("user.dir")
        println(f"Looking for data file in 'data' folder of the working directory: ${wd}")

        val source = Source.fromFile("./data/" + filename)
        val input = source.getLines.toVector
        source.close()

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

        // an Node class - description and capacity
        case class Vertex(descr: String, capacity: Int)

        // random nbr generator
        // https://stackoverflow.com/questions/34817917/how-to-pick-a-random-value-from-a-collection-in-scala
        // which is better maxBy or calling this method?
        val seed = new java.util.Date().hashCode
        val r = new scala.util.Random(seed)

        def choose[A](it: Iterator[A], r: util.Random): A =
            it.zip(Iterator.iterate(1)(_ + 1)).reduceLeft((x, y) =>
                if (r.nextInt(y._2) == 0) y else x
            )._1

        // read raw input and create an initial (incomplete) graph
        // dictionary of vertices
        val G0: mutable.Map[String, mutable.Set[String]] = mutable.Map[String, mutable.Set[String]]()
        val vertices = mutable.Set[String]()

        for x <- input do
            val line = x.split(":").map(x => x.trim)
            val k = line(0).trim
            G0 += (k -> mutable.Set[String]())
            vertices += k
            for token <- line(1).split("\\s+") do
                G0(k) += token
                if !vertices.contains(token) then
                    vertices += token

        // observe what was read in:
        G0.foreach(println)

        // 2nd pass - find and add any vertices not in the adjacency list/dictionary as keys
        val new_vertices = vertices.filter(!G0.keys.toSet.contains(_))

        val z=1

        // but the graph is incomplete. Refactor the raw input graph
        //   Q: The input data does not have a row for every node!
        //       A complete graph will need all the nodes to appear in the list
        //       with any edges node touches.
        //   A: I'll add all the nodes to a mutable ArrayBuffer

//        // G is full graph in adjacency list form
//        val G = mutable.Map[String, Vertex]()
//
//        // first pass - populate G with vertices read in from input file
//        G0.foreach(n => { G += (n._1 -> Vertex(n.descr, n._2.to(ArrayBuffer), Set(n._1))) })
//
//        // 2nd pass - add vertices found in input and not already in G
//        for v <- G0 do
//            // check each vertex this vertex is connected to.  if not in G, add it.
//            for n <- v._2.connectedTo do  // each node this node is connected to
//                if !G.contains(n) then
//                    G += (n -> Vertex(n, mutable.ArrayBuffer[String](), Set(n)))
//                if !G(n).connectedTo.contains(v._1) then
//                    G(n).connectedTo += v._1


        println("Completed reading input and building graph.")
        println(s"The graph has ${G0.size} nodes.")
        // println("The graph as adj list:")
        // G.foreach(println)
        println

        // Assert each vertex v in G does not contain duplicates
//        for v <- G0.keys do
//            if G(v).connectedTo.size != G(v).connectedTo.distinct.size then
//                println("Duplicate! Bad graph.")
//                System.exit(10)


        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()


        val S = ArrayBuffer[String]()
        val T = ArrayBuffer[String]()

        // print answer
        val answerP1 = S.length * T.length
        val ans1: fansi.Str = fansi.Color.Green(s"\n\nPart 1: Find the three wires you need to disconnect in order to divide the components into two separate groups.")
        println(ans1.overlay(fansi.Color.Green, 0, 7))
        println(s"What do you get if you multiply the sizes of these two groups together?  A: $answerP1")

        val delta1 = Duration.between(p1T0, Instant.now())
        println(f"Run time approx ${delta1.toMillis / 1000.0}%.3f seconds\n")



        // errata...
        // test fansi console output text coloring:
        // fansi
        //        val colored: fansi.Str = fansi.Color.Red("Hello World Ansi!")
        //        // Or fansi.Str("Hello World Ansi!").overlay(fansi.Color.Red)
        //
        //        val length = colored.length // Fast and returns the non-colored length of string
        //
        //        val blueWorld = colored.overlay(fansi.Color.Blue, 6, 11)
        //
        //        val underlinedWorld = colored.overlay(fansi.Underlined.On, 6, 11)
        //
        //        val underlinedBlue = blueWorld.overlay(fansi.Underlined.On, 4, 13)
        //        println(colored)
        //        println(blueWorld)
        //        println(underlinedWorld)
        //        println(underlinedBlue)

    }

end Day25_v2
