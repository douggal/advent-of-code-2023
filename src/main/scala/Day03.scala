import scala.io.Source
import java.time.{Duration, Instant}
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, Map, Set}

/** Advent of Code 2023 Day 3
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 3 puzzles.
 *
 * Created 3 Dec 2023
 * Link: https://adventofcode.com/2023/day/3
 */

class Day03 private(val title: String, val runType: Int):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run(): Unit = Day03.runPuzzle(runType)

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
        // for debug 2nd test file
        // val input = Source.fromResource("03-test-2.txt").getLines().toVector

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
        val symbols = "!@#$%^&*()-=+_/|\\[]{}<>,:;".toCharArray.toSet
        val digits = "0123456789".toCharArray.toSet
        val maxRow = input.length
        val maxCol = input.head.count(_ => true)

        /*
           Create a grid from the input data:  1-D array of Char
           Row-oriented, y-down
           This has an advantage: only need to consider what happens if number ends in last
           cell of a row if the end the grid has been reached.
           Ref:  (Amit's grid parts)[https://www.redblobgames.com/grids/parts/]
            (Chutes and Ladders)[https://docs.swift.org/swift-book/documentation/the-swift-programming-language/controlflow/]

            Index         0   1   2   3   4   5   6   7   8   9   10   11
            Data          4   6   7   .   .   1   1   4   .   .   .    .
            rows (y)      0   0   0   0   0   0   0   0   0   0   1    1
            columns (x)   0   1   2   3   4   5   6   7   8   9   0    1
        */
        val grid = ArrayBuffer[Char]()
        for li <- input do
            for c <- li.toCharArray do
                grid += c

        val z = 0

        def toIndex(r: Int, c: Int): Int = {
            // convert row and column to index in the grid
            // Formula = row * # cols in each row + column
            r * maxCol + c
        }

        def getNeighbors(r: Int, c: Int): ArrayBuffer[Char] = {
            // returns a Vector of the values of this cell's neighbors
            // neighbors share an edge with the cell a row r and column c
            // or share a corner touch - the diagonals

            val moves = List((-1, 0), (-1, 1), (0, 1), (1, 1), (1, 0), (1, -1), (0, -1), (-1, -1)) // clockwise from top
            val result = ArrayBuffer[Char]()
            for t <- moves do
                if r + t._1 >= 0 && r + t._1 < maxRow then
                    if c + t._2 >= 0 && c + t._2 < maxCol then
                        val x = grid(toIndex(r + t._1, c + t._2))
                        if symbols.contains(x) then result += x
            result
        }

        def getCoordsOfGear(r: Int, c: Int): (Int, Int) = {
            // circle the given cell in a clockwise direction
            // if a gear is found,
            // returns a Tuple2 with the coords of gear
            // neighbors share an edge with the cell a row r and column c
            // or share a corner touch - the diagonals

            val moves = List((-1, 0), (-1, 1), (0, 1), (1, 1), (1, 0), (1, -1), (0, -1), (-1, -1)) // clockwise from top
            var result: (Int,Int) = (0,0)
            for t <- moves do
                if r + t._1 >= 0 && r + t._1 < maxRow then
                    if c + t._2 >= 0 && c + t._2 < maxCol then
                        val x = grid(toIndex(r + t._1, c + t._2))
                        if x == '*' then
                            result = (r + t._1, c + t._2)
            result
        }

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        // Do some tests on test data:
        // Assert test data row 9, col 7 in test data returns index of 97
        // and test row 6 col 3 returns 63
        if runType == 1 then
            val test = toIndex(9,7)
            val test2 = toIndex(6,3)
            val neighbors = getNeighbors(4,2)
            val neighbors2 = getNeighbors(9,4)
            val abcd = getCoordsOfGear(2,4)
            val iiii = 0 // debug: for breakpoint
        end if

        // Algo: iterate down the grid left to right
        // if digit, then accumulate each cell's neighbors
        // when not a digit, then process number if has symbol neighbor

        // holding area in which to accumulate numbers as we go along
        val nums = ArrayBuffer[Int]()

        // define a couple of mutable helper variables
        var pow = 0  // power of 10
        var num = 0  // current number
        var hasSymbolNeighbor = false

        for row <- 0 until maxRow
            col <- 0 until maxCol do

            val cell = grid(toIndex(row, col))

            if digits.contains(cell) then
                // the cell contains a digit
                // multiply accumulating number, num, by 10, then add the new digit.
                if pow > 0 then
                    num = num*10
                num += cell.toString.toInt
                pow += 1
                if getNeighbors(row, col).length > 0 then
                    hasSymbolNeighbor = true
            else
                // reached a empty cell - if accumulating a number, then add new number
                if hasSymbolNeighbor then
                    nums += num
                // reset helpers
                pow = 0
                num = 0
                hasSymbolNeighbor = false
            end if

        // check if last cell in the grid was a digit,
        // and if so pick up the digit in last cell
        if hasSymbolNeighbor then
            nums += num

        val answerP1 = nums.sum

        println(s"Part 1: What is the sum of all of the part numbers in the engine schematic? A:  ${answerP1}")
        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Part 1 run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()

        // Algo: similar to part 1, we'll traverse the grid
        // left to right finding numbers, but only those numbers
        // touching a gear.  Also save the coordinates of the the gear cell.
        // Find the answer by iterating over Map of numbers, and for each
        // two (and only two says the instructions), multiple the two number together
        // and accumulate a sum.
        val gear = '*'

        // holding area in which to accumulate numbers as we go along
        // the value is a tuple, (part number, row, col) of the gear touches
        val ajoinsGear = scala.collection.mutable.ArrayBuffer[(Int,Int,Int)]()

        // define a couple of mutable helper variables
        var pow2 = 0 // power of 10
        var num2 = 0 // current number
        var gearCoords = (-1,-1)
        var hasGearNeighbor = false

        for row <- 0 until maxRow
            col <- 0 until maxCol do

            val cell = grid(toIndex(row, col))

            // Assumption:  every number is separated by a period or symbol from every other
            // even if number ends on a line, the next line then starts with symbol.
            if digits.contains(cell) then
                // the cell contains a digit
                // multiply accumulating number, num, by 10, then add the new digit.
                if pow2 > 0 then
                    num2 = num2 * 10
                num2 += cell.toString.toInt
                pow2 += 1
                if getNeighbors(row, col).contains(gear) then
                    hasGearNeighbor = true
                    gearCoords = getCoordsOfGear(row, col)
                end if
            else
                // reached a non-digit cell - if accumulating a number, then add new number
                if hasGearNeighbor then
                    val v = (num2, gearCoords._1, gearCoords._2)
                    ajoinsGear += v
                // reset helpers
                pow2 = 0
                num2 = 0
                hasGearNeighbor = false
                gearCoords = (-1,-1)
            end if

        // check if last cell in the grid was a digit,
        // and if so and is adjacent to a gear pick up the digit in last cell
        if hasGearNeighbor then
            val v = (num2, gearCoords._1, gearCoords._2)
            ajoinsGear += v

        // to solve, find each pair of numbers with same gear coord
        // multiply nums together, save in holding area, gearRatios, then compute the sum of the gearRatios
        // key in gearRatios map is the gear coordinates
        val gearRatios = mutable.Map[(Int, Int),BigInt]()
        val countIf = mutable.Set[(Int,Int)]()
        for (v, r, c) <- ajoinsGear do
            val key = (r,c)
            if !gearRatios.contains(key) then
                gearRatios += (key -> v)
            else
                gearRatios(key) = gearRatios(key) * v
                countIf += key  // only count if have 2 parts connected by a gear

        var sum:BigInt = 0
        for (k,v) <- gearRatios do
            if countIf.contains(k) then
                sum += gearRatios(k)

//        println("\n\nGear ratios:")
//        for v <- ajoinsGear do
//            if countIf.contains((v._2, v._3)) then
//                println(s"${v._1}\t${v._2}\t${v._3}")

        val answerP2 = sum

        println(s"Part 2: What is the sum of all of the gear ratios in your engine schematic?  A:  ${answerP2}")

        // 45752839 too low

        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Part 2 run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day03
