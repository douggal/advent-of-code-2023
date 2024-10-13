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
 * See Python solution in python folder
 *
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

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        // read raw input and create an initial (incomplete) graph
        // dictionary of vertices
        val G: mutable.Map[String, mutable.Set[String]] = mutable.Map[String, mutable.Set[String]]()
        val vertices = mutable.Set[String]()  // keep Set of all the vertices

        for x <- input do
            val line = x.split(":").map(x => x.trim)
            val k = line(0)
            G += (k -> mutable.Set[String]())
            vertices += k
            for token <- line(1).split("\\s+") do
                G(k) += token
                vertices += token

        // observe what was read in:
        println("The graph as read from input file:")
        G.foreach(println)

        // but the graph is incomplete. Refactor the raw input graph
        //   Q: The input data does not have a row for every node!
        //       A complete graph will need all the nodes to appear in the list
        //       with any edges node touches.
        //   A: Identify vertices not in graph and add vertices + all their connections
        val new_vertices = vertices.filter(!G.keys.toSet.contains(_))
        println(f"New vertices: ${new_vertices}")

        for newVertex <- new_vertices do
            // find what newVertex has an edge to, nvs = new vertices
            // https://stackoverflow.com/questions/40534642/scala-how-to-return-the-key-that-contains-an-item-in-their-value-where-value-is
            val connectedTo = G.collectFirst { case (k, v) if v.contains(newVertex) => k }

            connectedTo match {
                case None => ()  // Unit here equals do nothing
                case Some(vs) => G += (newVertex -> mutable.Set[String]().addAll(connectedTo))
            }
        val z=1

        // G is full graph in adjacency list form
        println("Completed reading input and building graph.")
        println(s"The graph has ${G.size} nodes.")
        println("The graph, G, represented as an adjacency list:")
        G.foreach(println)
        println

        // start with two Sets S and T
        // and pick two vertices at random
        // add to Set S
        // for every remaining vertex, v
        //   if v has more edges to vertices in S, then add to S
        //   else add to T
        // check number of edges between the vertices in the collections S and T
        val S: mutable.Map[String, mutable.Set[String]] = mutable.Map[String, mutable.Set[String]]()
        val T: mutable.Map[String, mutable.Set[String]] = mutable.Map[String, mutable.Set[String]]()

        val vs = vertices.toList
        S += (vs.head -> mutable.Set[String](vs.head))

        for vertex <- vs.tail do
            // collect all the vertices to which vertex is connected
            // in S
            val connectedToS = S.collectFirst { case (k, v) if v.contains(vertex) => k }
            // and in T
            val connectedToT = T.collectFirst { case (k, v) if v.contains(vertex) => k }

            if connectedToS.size < connectedToT.size then
                // S += (vertex -> mutable.Set[String]().addAll(connectedToS))
                S += (vertex -> mutable.Set[String]().addAll(connectedToS))
            else
                T += (vertex -> mutable.Set[String]().addAll(connectedToT))

        // print answer
        val answerP1 = S.size * T.size
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
