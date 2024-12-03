import days.Iday
import days.day1
import days.day2
import utility.FileReader
import java.io.File

fun main(args: Array<String>) {
    println("Advent of Code 2024")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    val day = 2
    val part = 2
    val suffix = ""

    println("Day $day part $part")

    val fileReader = FileReader()
    val fileName: String = "day" + day.toString() + "part" + part.toString() + suffix + ".txt"
    val altFileName: String = "day" + day.toString() + "part1" + suffix + ".txt"
    val filePath = "src\\main\\resources\\"
    var myInputFile = File(filePath + fileName)
    val lines: List<String>
    if (myInputFile.exists())
    {
        println("Filename: $fileName")
        lines = fileReader.readMyFile(myInputFile)
    }
    else
    {
        println("Filename: $altFileName")
        myInputFile = File(filePath + altFileName)
        lines = fileReader.readMyFile(myInputFile)
    }


    val myDay: Iday = when (day) {
        1 -> {
            day1()
        }
        2 -> day2()
        else -> day1()
    }

    when (part) {
        1 ->  myDay.part1(lines)
        2 ->  myDay.part2(lines)
        else -> {
            myDay.part1(lines)
            myDay.part2(lines)
        }
    }

}