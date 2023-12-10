import java.time.ZonedDateTime

@main
def Main(day: Int = 8, runType: Int = 1): Unit = {

    val title = "Advent of Code 2023"

    // for each day's puzzle, create an instance of class and supply an
    // input file type, called runType:
    // runType = 1 for test data
    //         = 2 for real data

     // val runType = 1 // Default to type =1, the test data

    // Which day's puzzle to run?
    // val day = 3

    // Implement each day's puzzle
    val d1 = Day01(s"$title Day 1", runType)
    val d2 = Day02(s"$title Day 2", runType)
    val d3 = Day03(s"$title Day 3", runType)
    val d4 = Day04(s"$title Day 4", runType)
    val d5 = Day05(s"$title Day 5", runType)
    val d6 = Day06(s"$title Day 6", runType)
    val d7 = Day07(s"$title Day 7", runType)
    val d8 = Day08(s"$title Day 8", runType)
    val d9 = Day09(s"$title Day 9", runType)

    // Run the selected day's puzzle
    println(s"Begin: ${ZonedDateTime.now()}")

    day match {
        case 9 => d9.run()
        case 8 => d8.run()
        case 7 => d7.run()
        case 6 => d6.run()
        case 5 => d5.run()
        case 4 => d4.run()
        case 3 => d3.run()
        case 2 => d2.run()
        case 1 => d1.run()
        case _ => println("Error day does not match with a class")
    }

    println(s"\nEnd: ${ZonedDateTime.now()}")
}
