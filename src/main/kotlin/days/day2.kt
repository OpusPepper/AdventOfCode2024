package days

import kotlin.math.abs

class day2 : Iday {
    override fun part1(inputLines: List<String>) {
        var numberOfSafeRows = 0

        for(l in inputLines) {
            val eachNumberOnRow: List<Int> = l.split(Regex("\\s+")).map { it.toInt()}
            println("Line: $l")
            var isSafe: Boolean
            val listDiffs = mutableListOf<Int>()
            for(i in 1 until eachNumberOnRow.size){
                val number1 = eachNumberOnRow.elementAt(i - 1)
                val number2 = eachNumberOnRow.elementAt(i)
                val diff = number2 - number1
                listDiffs.add(diff)
                print("   $number1, $number2, $diff ")
                if (diff > 0) {
                    print(" - increasing")
                }
                else if (diff < 0) {
                    print(" - decreasing")
                }
                else {
                    print(" - zero-gain")
                }
                println()
            }
            isSafe = isAllIncreasingOrDescreasing(listDiffs) &&
                    isAllWithinDiffRangeOf1to3(listDiffs)
            when(isSafe) {
                true -> {
                    println(" row is safe")
                    numberOfSafeRows+= 1
                }
                else -> {
                    println(" row is unsafe")
                }
            }
        }
        println("Number of safe reports: $numberOfSafeRows")
    }

    private fun isAllWithinDiffRangeOf1to3(listDiffs: MutableList<Int>) =
        (listDiffs.filter { abs(it) == 1 || abs(it) == 2 || abs(it) == 3 }.size == listDiffs.size)

    private fun isAllIncreasingOrDescreasing(listDiffs: MutableList<Int>) =
        ((listDiffs.filter { it > 0 }.size == listDiffs.size) ||
                (listDiffs.filter { it < 0 }.size == listDiffs.size))

    override fun part2(inputLines: List<String>) {
        var numberOfSafeRows = 0
        val inputLinesNotSafe = mutableListOf<String>()

        println("Number of input lines: ${inputLines.size}")
        for(l in inputLines) {
            val eachNumberOnRow: MutableList<Int> = l.split(Regex("\\s+")).map { it.toInt()}.toMutableList()
            println("Line: $l")
            var isSafe: Boolean
            val listDiffs = getListOfDiffs(eachNumberOnRow)

            isSafe = (isAllIncreasingOrDescreasing(listDiffs) && isAllWithinDiffRangeOf1to3(listDiffs))

            when(isSafe) {
                true -> {
                    println(" row is safe")
                    numberOfSafeRows+= 1
                }
                else -> {
                    println(" row is unsafe ")
                    inputLinesNotSafe.add(l)
                }
            }
        }
        println("Number of safe reports: $numberOfSafeRows")
        println("Number of unsafe reports: ${inputLinesNotSafe.size}")
        // Try to reprocess unsafe reports to see if we can make them safe
        // Brute force
        for (l in inputLinesNotSafe) {
            val eachNumberOnRow: MutableList<Int> = l.split(Regex("\\s+")).map { it.toInt() }.toMutableList()
            println("Line: $l")
            var isSafe = false

            for (i in eachNumberOnRow.indices) {
                if (!isSafe) {
                    var skipIndexEachNumberOnRow: MutableList<Int>
                    skipIndexEachNumberOnRow = getNewNumberOnRowListSkipIndex(eachNumberOnRow, i)
                    println("  Line: $skipIndexEachNumberOnRow")
                    val listDiffs = getListOfDiffs(skipIndexEachNumberOnRow)

                    isSafe =
                        isSafe || (isAllIncreasingOrDescreasing(listDiffs) && isAllWithinDiffRangeOf1to3(listDiffs))
                }  // Once it's SAFE there is no need to process further
            }
            when(isSafe) {
                true -> {
                    println(" row has been made safe")
                    numberOfSafeRows+= 1
                }
                else -> {
                    println(" row is still unsafe ")
                }
            }
        }

        println("Number of safe reports: $numberOfSafeRows")
    }

    private fun getListOfDiffs(listOfInputInts: List<Int>) : MutableList<Int> {
        val listDiffs = mutableListOf<Int>()
        for(i in 1 until listOfInputInts.size){
            val number1 = listOfInputInts.elementAt(i - 1)
            val number2 = listOfInputInts.elementAt(i)
            val diff = number2 - number1
            listDiffs.add(diff)
            print("   $number1, $number2, $diff ")
            if (diff > 0) {
                print(" - increasing")
            }
            else if (diff < 0) {
                print(" - decreasing")
            }
            else {
                print(" - zero-gain")
            }
            println()
        }
        return listDiffs
    }

    private fun getNewNumberOnRowListSkipIndex(eachNumberOnRow: List<Int>, indexToSkip: Int) : MutableList<Int> {
        val newList = mutableListOf<Int>()
        for (n in eachNumberOnRow.indices) {
            if (n != indexToSkip) {
                newList.add(eachNumberOnRow.elementAt(n))
            }
        }

        return newList
    }
}