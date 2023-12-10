import scala.io.Source
import java.time.{Duration, Instant}
import scala.collection.mutable.ArrayBuffer

/** Advent of Code 2023 Day 10
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 10 puzzles.
 *
 * Created 10 Dec 2023
 * Link: https://adventofcode.com/2023/day/10
 */

class Day10 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day10.solution(runType)

    override def toString: String = s"Class ${Day10.puzzleTitle}"

end Day10

object Day10:

    val day = "10"
    val puzzleTitle = "Day 10: Pipe Maze"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day10 = {
        new Day10("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day10 = {
        new Day10(title, runType)
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
        // represent pipe maze as an adjacency list
        // in a 1-D list of tiles (nodes)
        // Each tile has an ID, the index of its position in the list, a face symbol, and
        // a List of nodes to which it is connected/adjacent
        // The degree of each node is given by count of connected nodes
        val symbols = ("SF-|7J").toCharArray.toSet
        val maxRow = input.length
        val maxCol = input.head.count(_ => true)

        // clockwise from top, no diagonals, Tuple is (row, column), and orientation is y/row-down
        val neighbors = List((-1, 0), (0, 1), (1, 0), (0, -1))
        val neighborsF = List((0,1),(1,0))
        val neighbors7 = List((0,-1),(1,0))
        val neighborsJ = List((-1,0),(0,-1))
        val neighborsL = List((-1,0),(0,1))
        val neighborsPipe = List((-1,0),(1,0))
        val neighborsDash = List((0,-1),(0,1))

        def toIndex(r: Int, c: Int): Int = {
            // convert row and column to index in the grid
            r * maxCol + c
        }

        def getOneNeighbor(rr: Int, cc: Int): Option[Int] = {
            if rr >= 0 && rr < maxRow && cc >= 0 && cc < maxCol && input(rr)(cc) != '.' then
                Option(toIndex(rr, cc))
            else
                None
        }

        def getNeighbors(r: Int, c: Int, symbol: Char): List[Int] = {
            val ns = ArrayBuffer[Int]() // this tiles neighbors

            // where to look?  depends on what symbol this tile contains
            symbol match
                case 'S' => {
                    // special case - cover all 8 neighbors, should be connection to only 2
                    for t <- neighbors do
                        val x = getOneNeighbor(r + t._1, c + t._2)
                        x match
                            case Some(value) => ns += value
                            case None => () /* no action */
                    if ns.length > 2 || ns.length == 0 then
                        println("Error - bad S tile")
                }
                case 'F' => {
                    // right, and down
                    for t <- neighborsF do
                        val x = getOneNeighbor(r + t._1, c + t._2)
                        x match
                            case Some(value) => ns += value
                            case None => ()
                }
                case '-' => {
                    // right, left
                    for t <- neighborsDash do
                        val x = getOneNeighbor(r + t._1, c + t._2)
                        x match
                            case Some(value) => ns += value
                            case None => () /* no action */
                }
                case '|' => {
                    // top bottom
                    for t <- neighborsPipe do
                        val x = getOneNeighbor(r + t._1, c + t._2)
                        x match
                            case Some(value) => ns += value
                            case None => () /* no action */
                }
                case '7' => {
                    // down and left
                    for t <- neighbors7 do
                        val x = getOneNeighbor(r + t._1, c + t._2)
                        x match
                            case Some(value) => ns += value
                            case None => () /* no action */
                }
                case 'J' => {
                    // top and left
                    for t <- neighborsJ do
                        val x = getOneNeighbor(r + t._1, c + t._2)
                        x match
                            case Some(value) => ns += value
                            case None => () /* no action */
                }
                case 'L' => {
                    // top and right
                    for t <- neighborsL do
                        val x = getOneNeighbor(r + t._1, c + t._2)
                        x match
                            case Some(value) => ns += value
                            case None => () /* no action */
                }
                case _ => () /* no action */

            ns.toList
        }

        case class Tile(id: Int, face: Char, isConnectedTo: List[Int])

        val pipeMaze = ArrayBuffer[Tile]()
        for li <- input.zipWithIndex do
            for c <- li._1.zipWithIndex do
                val i = toIndex(li._2,c._2)
                val ns = getNeighbors(li._2,c._2,c._1)
                if ns.length > 0 then
                    pipeMaze += Tile(i,c._1,ns)
                else
                    pipeMaze += Tile(i,c._1, List())

        pipeMaze.foreach(x => println(s"Tile ${x.id} has Face ${x.face}, and is Connected to Tiles: ${x.isConnectedTo.mkString(",")}"))

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        /*
        If you want to get out ahead of the animal, you should find the tile in the loop that is farthest from
        the starting position. Because the animal is in the pipe, it doesn't make sense to measure this by
        direct distance. Instead, you need to find the tile that would take the longest number of steps
        along the loop to reach from the starting point - regardless of which way around the loop the animal went.
         */

        //  ??? DFS !


        val answerP1 = 0
        println(s"Part 1: Find the single giant loop starting at S. How many steps along the loop does it take to get ")
        println(s"from the starting position to the point farthest from the starting position?   A: $answerP1")

        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Part 1 run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()



        val answerP2 = 0
        println(s"Part 2: TBD ???  A: $answerP2?")

        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Part 2 run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day10
