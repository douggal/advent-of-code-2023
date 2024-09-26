import scala.io.Source
import java.time.{Duration, Instant}
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, Map}
import fansi.*

/** Advent of Code 2023 Day 25
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 25 puzzles.
 *
 * Created 25 Dec 2023
 * Link: https://adventofcode.com/2023/day/25
 *
 * 2nd try 31 May 2024
 */

class Day25 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day25.solution(runType)

    override def toString: String = s"Class ${Day25.puzzleTitle}"

end Day25

object Day25:

    val day = "25"
    val puzzleTitle = "Day 25: Snowverload"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day25 = {
        new Day25("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day25 = {
        new Day25(title, runType)
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
        println(f"Working directory is: ${wd}")

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

        // an Node class - connects 0 or more other nodes
        case class Node(descr: String, isConnectedTo: List[String])

        // a Vertex with memory of what vertices were used to create it
        case class Vertex(ID: String,
                          connectedTo: mutable.ArrayBuffer[String],
                          madeFrom: Set[String])

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
        // pretty cool - creates an immutable data structure direct from input file
        val inputgraph: List[Node] =
            input
                .map(x => x.split(":"))
                .map(x => Node(x(0).trim, x(1).trim.split(" +").toList))
                .toList

        // observe what was read in:
        // inputgraph.foreach(println)

        // but the graph is incomplete. Refactor the raw input graph
        //   Q: The input data does not have a row for every node!
        //       A complete graph will need all the nodes to appear in the list
        //       with any edges node touches.
        //   A: I'll add all the nodes to a mutable ArrayBuffer

        // G is full graph in adjacency list form
        val G = mutable.Map[String, Vertex]()

        // first pass - populate G with vertices read in from input file
        inputgraph.foreach(n => { G += (n._1 -> Vertex(n.descr, n._2.to(ArrayBuffer), Set(n._1))) })

        // 2nd pass - add vertices found in input and not already in G
        for v <- G do
            // check each vertex this vertex is connected to.  if not in G, add it.
            for n <- v._2.connectedTo do  // each node this node is connected to
                if !G.contains(n) then
                    G += (n -> Vertex(n, mutable.ArrayBuffer[String](), Set(n)))
                if !G(n).connectedTo.contains(v._1) then
                    G(n).connectedTo += v._1


        println("Completed reading input and building graph.")
        println(s"The graph has ${G.size} nodes.")
        // println("The graph as adj list:")
        // G.foreach(println)
        println

        // Assert each vertex v in G does not contain duplicates
        for v <- G.keys do
            if G(v).connectedTo.size != G(v).connectedTo.distinct.size then
                println("Duplicate! Bad graph.")
                System.exit(10)

        // Make a deep copy of G
        // https://stackoverflow.com/questions/9049117/copy-contents-of-immutable-map-to-new-mutable-map/9049302
        val Gprime = mutable.Map[String, Vertex]()
        for n <- G.keys do
            Gprime += (n -> Vertex(n, G(n).connectedTo.map(x => x),G(n).madeFrom.map(x => x)))

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        // Try 1
        // Q: are there any obvious partitions around nodes with small number of connections ?
        // println(s"The nodes with small nbr connections:")
        // G.filter(x => x._2.size < 5).foreach(println)
        // println(s"The nodes with many connections:")
        // G.filter(x => x._2.size >= 9).foreach(println)
        // A: no
        // End Try 1 - fail


        // Try 3
        // 31 May 2024 - Try Karger's Algorithm

        /* Karger's algo, as I understand it, applied here:
            We know a solution exists and min cut == 3 edges
            Divide graph G into two components, S and T by collapsing together vertices chosen at random
            Count edges connecting S and T
            Repeat until the # edges between two components == 3 then we're done
         */

        val S = mutable.ArrayBuffer[String]()
        val T = mutable.ArrayBuffer[String]()
        var found = false
        var i = 0
        while !found && i < 1e6 do
            i+=1
            S.clear()
            T.clear()

            // Candidate graph - start with a deep copy of G
            val Gcand = mutable.Map[String, Vertex]()
            for n <- G.keys do
                Gcand += (n -> Vertex(n, G(n).connectedTo.map(x => x),G(n).madeFrom.map(x => x)))

            while Gcand.size > 2 do
                // select two nodes at random using a uniform distribution
                // https://stackoverflow.com/questions/34817917/how-to-pick-a-random-value-from-a-collection-in-scala
                val n1 = choose(Gcand.iterator, r)._1 // Gcand.maxBy(_=> r.nextInt)._1
                val n2 = choose(Gcand.iterator, r)._1 // Gcand.maxBy(_=> r.nextInt)._1
                if n1 != n2 then
                    // Collapse node n1 with n2 and create a new node, named n1,
                    // and with edges that are the combined edges of n1 and n2
                    // the new node is made from everything that make up n1 and n2 + n1 and n2 themselves

                    // combine the 'madeFrom' vertices together
                    val mf = mutable.Set[String]()
                    for (x <- Gcand(n1).madeFrom) do
                        mf += x
                    for (x <- Gcand(n2).madeFrom) do
                        mf += x

                    // create new vertex
                    val newNode = (n1 -> Vertex(n1, mutable.ArrayBuffer[String](), mf.toSet))

                    // new node is connected to the combined vertexes of n1 and n2
                    val newVertices = mutable.Set[String]()
                    for v <- Gcand(n1).connectedTo do
                        if v != newNode._1 then
                            newVertices += v
                    for v <- Gcand(n2).connectedTo do
                        if v != newNode._1 then
                            newVertices += v
                    for v <- newVertices do
                        newNode._2.connectedTo += v

                    // delete old vertices n1 and n2 and add new combined vertex
                    Gcand -= n1
                    Gcand -= n2
                    Gcand += newNode

                    // and for each node in graph, replace edges to n2 with edge to newNode
                    for v <- Gcand.keys do
                        Gcand(v).connectedTo -= n2
                        if !Gcand(v).connectedTo.contains(newNode._1) && newNode._1 != v then
                            Gcand(v).connectedTo += newNode._1
                    end for
                end if
            end while

            // candidate graph Gcand has two groups left, 0 and 1
            for s <- Gcand.head._2.madeFrom do
                S += s
            for t <- Gcand.last._2.madeFrom do
                T += t

            // Assertions to ascertain if the candidate solution is plausible:
            if Gcand.size != 2 then
                println("It failed! More than 2 groups.")
                System.exit(1)
            if S.intersect(T).nonEmpty then
                println("It failed S intersect T is not empty!")
                println(S.sortWith(_ < _).mkString(", "))
                println(T.sortWith(_ < _).mkString(", "))
                System.exit(2)
            if S.length + T.length != Gprime.size then
                println("It failed! Nbr nodes S + T != nbr nodes in Gprime")
                System.exit(3)

            // how do I count edges between the two groups ???
            // look at each node in 1st group
            //  does it connect to a node in 2nd group found by ref to original graph ?
            var cnt = 0
            for s <- S do
                for t <- T do
                    if Gprime(t).connectedTo.contains(s) then
                        cnt += 1

            if cnt == 3 && S.size > 1 && T.size > 1 then
                found = true

            // heartbeat and error check  sum of S and T should equal # nodes and always be the same
            if found || i % 10 == 0 then
                print(f"$i%6d iterations min cut (S,T) = (${S.length}%3d,${T.length}%3d)")
                println(f", N edges S-T = ${cnt}%4d")
        end while

        // check answer
        println(s"Size of 'S' group of components: ${S.length}")
        print("S: ")
        print(S.sortWith(_ < _).mkString(", "))
        println(s"\nSize of 'T' group of components: ${T.length}")
        print("T: ")
        print(T.sortWith(_ < _).mkString(", "))

        // print answer
        val answerP1 = S.length * T.length
        val ans1: fansi.Str = fansi.Color.Red(s"\n\nPart 1: Find the three wires you need to disconnect in order to divide the components into two separate groups.")
        println(ans1.overlay(fansi.Color.Blue, 0, 7))
        println(s"What do you get if you multiply the sizes of these two groups together?  A: $answerP1")

        val delta1 = Duration.between(p1T0, Instant.now())
        println(f"Run time approx ${delta1.toMillis / 1000.0}%.3f seconds\n")



        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()



        val answerP2 = 0
        println(s"Part 2: TBD ???  A: $answerP2")

        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Run time approx ${delta2.toMillis} milliseconds")

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


        // Try 2 - this failed
        // Q: are there any bridges - graph components with only one edge between them?
        // A: try to find bridges if they exist.

        // Q: The algos often use small integers to label the nodes
        //    and the small ints are used to index arrays.
        //    How can I label the nodes as integers?
        // A: Make 2 maps to translate each 3-char string description to an ID and vice versa
        // toID - assign and ID number to each 3 char description
        // toNode - revers toID - given ID map to node char description
        // val toID = G.keys.zipWithIndex.toMap
        // val toNode = toID.map(x => (x._2, x._1))

        // in each ArrayBuffer the index is the ID of of the node as assigned above
        //        val ids = ArrayBuffer[Int]()
        //        val low = ArrayBuffer[Int]()
        //        val visited = ArrayBuffer[Boolean]()
        //        for x <- G.values do
        //            visited += false
        //            ids += 0
        //            low += 0
        //
        //        var id = 0
        //        def dfs(at: Int, parent:Int, bridges:ArrayBuffer[Int]): Unit = {
        //            visited(at) = true
        //            id += 1
        //            ids(at) = id
        //            low(at) = ids(at)
        //
        //            // from each edge from node "at" to node "to"
        //            for to <- G(toNode(at)) do
        //                val too = toID(to)
        //                if too != parent then
        //                    if ! visited(too) then
        //                        dfs(too, at, bridges)
        //                        low(at) = Math.min(low(at), low(too))
        //                        if ids(at) < low(too) then
        //                            bridges += at
        //                            bridges += too
        //                        end if
        //                    else
        //                        low(at) = Math.min(low(at), ids(too))
        //                    end if
        //                end if
        //            end for
        //        }
        //
        //        def findBridges(): ArrayBuffer[Int] = {
        //            val bridges = ArrayBuffer[Int]()
        //            // for each node by its ID in nodes Map
        //            for node <- toNode.keys.toList.sorted do
        //                if ! visited(node) then
        //                    dfs(node, -1, bridges)
        //            bridges
        //        }
        //
        //        val bridges = findBridges()
        //
        //        println(s"\nNumber Bridges: ${bridges.length/2}")
        //        bridges.grouped(2).foreach(x => println(s"${toNode(x(0))} -  ${toNode(x(1))}"))
        //
        //        if bridges.isEmpty then
        //            println("Outcome:  no go, there are no bridges in this graph")

        // End Try 2 - fail

    }

end Day25
